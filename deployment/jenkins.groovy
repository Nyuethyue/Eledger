def serviceHosts = env.hosts.split(",").collect { it.trim() }
def branchToDeploy = env.branch ?: 'master'
def targetEnv = env.ENV ?: 'dev'
def dbUser = env.dbUser ?: 'bu_eledger'
def dbName = env.dbName ?: dbUser
def dbChangelogFileName = env.dbChangelogFileName ?: 'changelog.prod.xml'
def dbPassword = null

try {
    withCredentials([
            string(credentialsId: "${targetEnv}_db_eledger_password", variable: '_dbPassword')
    ]) {
        dbPassword = _dbPassword
    }
    println 'DB credentials provided, liquibase stage will be applied'
} catch (e) {
    println "No DB credentials provided, liquibase stage will be skipped, [${e.message}]"
}

pipeline {

    agent any

    tools {
        jdk "Openjdk_JDK_11.0.1"
    }

    stages {
        stage("Cleanup") {
            steps {
                deleteDir()
                cleanWs()
            }
        }
        stage('Checkout') {
            steps {
                git branch: branchToDeploy,
                        credentialsId: 'git',
                        url: 'http://10.11.1.23:7990/scm/bhut_sync/eledger.git'
            }
        }
        stage('Check DB') {
            when {
                expression { dbPassword }
            }
            steps {
                script {
                    hideShell "./gradlew status -Pdbname='${dbName}' -Puser='${dbUser}' -Ppass='${dbPassword}' -Phostname='${dbHost}' -PchangelogFileName=${dbChangelogFileName}"
                }
            }
        }
        stage('Package') {
            steps {
                // add test task
                sh "./gradlew bootJar"
            }
        }
        stage('Deploy') {
            steps {
                script {
                    prepareServer(serviceHosts)
                    deployModule(serviceHosts)
                }
            }
        }

        stage('Update DB') {
            when {
                expression { dbPassword }
            }
            steps {
                script {
                    hideShell "./gradlew update -Pdbname='${dbName}' -Puser='${dbUser}' -Ppass='${dbPassword}' -Phostname='${dbHost}' -PchangelogFileName=${dbChangelogFileName}"
                }
            }
        }

        stage('Reload') {
            steps {
                script {
                    reloadServer(serviceHosts)
                }
            }
        }
    }
}

def prepareServer(hosts) {
    hosts.each {
        sh "ssh -qT for.deploy@${it} 'mkdir -p /opt/eledger && mkdir -p /opt/eledger/config'"
    }
}

def deployModule(hosts) {
    hosts.each {
        sh "scp -p -o StrictHostKeyChecking=no build/libs/eledger.jar for.deploy@${it}:/opt/eledger/"
        sh "scp -p -o StrictHostKeyChecking=no deployment/config/application.properties for.deploy@${it}:/opt/eledger/config/application.properties-sample"
        sh "scp -p -o StrictHostKeyChecking=no deployment/service/eledger.service for.deploy@${it}:/opt/eledger/eledger.service-sample"
    }
}

def reloadServer(hosts) {
    hosts.each {
        sh "ssh -qT for.deploy@${it} 'sudo systemctl restart eledger.service'"
    }
}

def hideShell(cmd) {
    sh('#!/bin/sh -e\nset +x\n' + cmd + '\nset -x\n')
}