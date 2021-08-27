package bhutan.eledger;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaRepositories(repositoryBaseClass = CustomQuerydslJpaRepositoryImpl.class)
public class EledgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EledgerApplication.class, args);
	}

}
