package bugfree.challenge.client_api.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import bugfree.challenge.domain.entities.idclasses.UserId;

/** Utility class for handling authentication and security context operations */
@Component
public class SecurityUtils {

  /**
   * Gets the currently authenticated user's ID as a string
   *
   * @return the user ID from the security context
   */
  public static String getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  /**
   * Gets the currently authenticated user's ID as a UserId domain object
   *
   * @return the user ID wrapped in a UserId object
   */
  public static UserId getCurrentUserIdAsObject() {
    String userId = getCurrentUserId();
    return new UserId(userId);
  }

  /**
   * Gets the current authentication object
   *
   * @return the Authentication object from the security context
   */
  public static Authentication getCurrentAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
