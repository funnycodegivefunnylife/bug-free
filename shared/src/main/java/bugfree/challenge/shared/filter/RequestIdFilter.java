package bugfree.challenge.shared.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import bugfree.challenge.shared.mdc.RequestIdMDC;

/**
 * Filter to extract or generate request ID for each HTTP request This filter should be executed
 * before any other filters that might use the request ID
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      // Try to get request ID from header
      String requestId = request.getHeader(RequestIdMDC.REQUEST_ID_HEADER);

      // If not present, generate a new one
      if (!StringUtils.hasText(requestId)) {
        requestId = RequestIdMDC.generateAndSetRequestId();
      } else {
        RequestIdMDC.setRequestId(requestId);
      }

      // Add request ID to response header for client tracking
      response.setHeader(RequestIdMDC.REQUEST_ID_HEADER, requestId);

      filterChain.doFilter(request, response);
    } finally {
      // Clean up MDC after request processing
      RequestIdMDC.clearAll();
    }
  }
}
