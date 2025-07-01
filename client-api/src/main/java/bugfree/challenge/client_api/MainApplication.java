package bugfree.challenge.client_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	scanBasePackages = {"bugfree.challenge"},
	exclude = {
		RedisAutoConfiguration.class
	}
)
@EnableJpaRepositories(basePackages = "bugfree.challenge.infrastructure.persistence.jpa")
@EntityScan(basePackages = "bugfree.challenge.infrastructure.persistence.entities")
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
