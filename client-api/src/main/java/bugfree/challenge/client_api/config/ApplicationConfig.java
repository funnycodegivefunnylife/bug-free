package bugfree.challenge.client_api.config;

import bugfree.challenge.domain.repositories.UserRepository;
import bugfree.challenge.infrastructure.persistence.repositories.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for dependency injection
 */
@Configuration
public class ApplicationConfig {
    
    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository) {
        return jpaUserRepository;
    }
}
