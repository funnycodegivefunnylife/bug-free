package bugfree.challenge.shared.interceptor;

import bugfree.challenge.shared.mdc.RequestIdMDC;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor to handle MDC context for request processing
 * This interceptor ensures request ID is available throughout the request lifecycle
 */
@Component
public class MDCInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(MDCInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get or generate request ID (should already be set by RequestIdFilter, but add fallback)
        String requestId = RequestIdMDC.getRequestId();
        if (!StringUtils.hasText(requestId)) {
            requestId = request.getHeader(RequestIdMDC.REQUEST_ID_HEADER);
            if (!StringUtils.hasText(requestId)) {
                requestId = RequestIdMDC.generateAndSetRequestId();
            } else {
                RequestIdMDC.setRequestId(requestId);
            }
        }
        
        logger.debug("Processing request: {} {} with request ID: {}", 
                    request.getMethod(), request.getRequestURI(), requestId);
        
        // Add request ID to response header if not already present
        if (!response.containsHeader(RequestIdMDC.REQUEST_ID_HEADER)) {
            response.setHeader(RequestIdMDC.REQUEST_ID_HEADER, requestId);
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Log completion of request processing
        String requestId = RequestIdMDC.getRequestId();
        logger.debug("Completed processing request: {} {} with request ID: {}", 
                    request.getMethod(), request.getRequestURI(), requestId);
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Log any exceptions and clean up is handled by RequestIdFilter
        if (ex != null) {
            String requestId = RequestIdMDC.getRequestId();
            logger.error("Exception occurred during request processing for request ID: {}", requestId, ex);
        }
        
        // Note: MDC cleanup is handled by RequestIdFilter to ensure it happens after all filters/interceptors
    }
}
