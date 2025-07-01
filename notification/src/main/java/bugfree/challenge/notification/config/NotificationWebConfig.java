package bugfree.challenge.notification.config;

import bugfree.challenge.shared.interceptor.MDCInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for notification module with MDC interceptor
 * Only activated if this module runs as a web application
 */
@Configuration
@ConditionalOnWebApplication
public class NotificationWebConfig implements WebMvcConfigurer {
    
    private final MDCInterceptor mdcInterceptor;
    
    @Autowired
    public NotificationWebConfig(MDCInterceptor mdcInterceptor) {
        this.mdcInterceptor = mdcInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcInterceptor)
                .addPathPatterns("/**")      // All endpoints if any
                .excludePathPatterns(
                    "/actuator/**",          // Spring Boot actuator endpoints
                    "/health",               // Health check
                    "/metrics",              // Metrics endpoint
                    "/error"                 // Error page
                );
    }
}
