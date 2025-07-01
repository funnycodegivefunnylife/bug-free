package bugfree.challenge.shared.mdc;

import java.util.UUID;

import org.slf4j.MDC;

/** Utility class for managing request ID in MDC (Mapped Diagnostic Context) */
public class RequestIdMDC {

  public static final String REQUEST_ID_KEY = "request_id";
  public static final String REQUEST_ID_HEADER = "X-Request-ID";

  /**
   * Set a request ID in the MDC context
   *
   * @param requestId the request ID to set
   */
  public static void setRequestId(String requestId) {
    MDC.put(REQUEST_ID_KEY, requestId);
  }

  /**
   * Get the current request ID from MDC context
   *
   * @return the current request ID or null if not set
   */
  public static String getRequestId() {
    return MDC.get(REQUEST_ID_KEY);
  }

  /**
   * Generate and set a new UUID-based request ID
   *
   * @return the generated request ID
   */
  public static String generateAndSetRequestId() {
    String requestId = UUID.randomUUID().toString();
    setRequestId(requestId);
    return requestId;
  }

  /** Clear the request ID from MDC context */
  public static void clearRequestId() {
    MDC.remove(REQUEST_ID_KEY);
  }

  /** Clear all MDC context */
  public static void clearAll() {
    MDC.clear();
  }

  /**
   * Execute a runnable with a specific request ID, then clear it
   *
   * @param requestId the request ID to use
   * @param runnable the code to execute
   */
  public static void withRequestId(String requestId, Runnable runnable) {
    String previousRequestId = getRequestId();
    try {
      setRequestId(requestId);
      runnable.run();
    } finally {
      if (previousRequestId != null) {
        setRequestId(previousRequestId);
      } else {
        clearRequestId();
      }
    }
  }
}
