package bhutan.eledger;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackageClasses = EledgerApplication.class, scanBasePackages = {"am.iunetworks.lib.task"})
@ConfigurationPropertiesScan
@EntityScan(basePackageClasses = EledgerApplication.class, basePackages = {"am.iunetworks.lib.task"})
@EnableEnversRepositories(basePackageClasses = EledgerApplication.class, basePackages = {"am.iunetworks.lib.task"}, repositoryBaseClass = CustomQuerydslJpaRepositoryImpl.class)
public class EledgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EledgerApplication.class, args);
    }

}
