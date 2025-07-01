package bugfree.challenge.client_api.config;

import bugfree.challenge.shared.interceptor.MDCInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for client-api module with MDC interceptor
 */
@Configuration
public class ClientApiWebConfig implements WebMvcConfigurer {
    
    private final MDCInterceptor mdcInterceptor;
    
    @Autowired
    public ClientApiWebConfig(MDCInterceptor mdcInterceptor) {
        this.mdcInterceptor = mdcInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcInterceptor)
                .addPathPatterns("/api/**")  // Only API endpoints
                .excludePathPatterns(
                    "/api/health",           // Health check
                    "/actuator/**",          // Spring Boot actuator endpoints
                    "/swagger-ui/**",        // Swagger UI
                    "/v3/api-docs/**",       // OpenAPI docs
                    "/error"                 // Error page
                );
    }
}
