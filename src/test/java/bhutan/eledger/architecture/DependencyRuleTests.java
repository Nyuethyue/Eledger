package bhutan.eledger.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DependencyRuleTests {

    @Test
    void validateRegistrationContextArchitecture() {
        HexagonalArchitecture.boundedContext("bhutan.eledger")

                .withDomainLayer("domain")

                .withAdaptersLayer("adapter")
                .incoming("in.web")
                .outgoing("out.persistence")
                .and()

                .withApplicationLayer("application")
                .services("service")
                .incomingPorts("port.in")
                .outgoingPorts("port.out")
                .and()

                .withConfiguration("configuration")
                .check(new ClassFileImporter()
                        .importPackages("bhutan.eledger.."));
    }

    @Test
    void testPackageDependencies() {
        noClasses()
                .that()
                .resideInAPackage("bhutan.eledger.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("bhutan.eledger.application..")
                .check(new ClassFileImporter()
                        .importPackages("bhutan.eledger.."));

        noClasses()
                .that()
                .resideInAPackage("bhutan.eledger.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("bhutan.eledger.adapter..")
                .check(new ClassFileImporter()
                        .importPackages("bhutan.eledger.."));

        noClasses()
                .that()
                .resideInAPackage("bhutan.eledger.application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("bhutan.eledger.adapter..")
                .check(new ClassFileImporter()
                        .importPackages("bhutan.eledger.."));

        noClasses()
                .that()
                .resideInAPackage("bhutan.eledger.adapter.out.persistence..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("bhutan.eledger.web..")
                .check(new ClassFileImporter()
                        .importPackages("bhutan.eledger.."));
    }

}
