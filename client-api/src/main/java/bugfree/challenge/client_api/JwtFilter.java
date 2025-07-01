package bugfree.challenge.client_api;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(2) // Execute after RequestIdFilter
public class JwtFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    logger.debug("Processing JWT authentication for request: {}", request.getRequestURI());

    // TODO: Implement JWT validation and set authentication in SecurityContextHolder
    // The request ID is already available in MDC context from RequestIdFilter

    filterChain.doFilter(request, response);
  }
}
