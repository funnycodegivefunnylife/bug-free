package bugfree.challenge.shared.mdc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

/** Test class for RequestIdMDC utility */
public class RequestIdMDCTest {

  @BeforeEach
  void setUp() {
    MDC.clear();
  }

  @AfterEach
  void tearDown() {
    MDC.clear();
  }

  @Test
  void testSetAndGetRequestId() {
    String testRequestId = "test-request-123";

    RequestIdMDC.setRequestId(testRequestId);

    assertEquals(testRequestId, RequestIdMDC.getRequestId());
    assertEquals(testRequestId, MDC.get(RequestIdMDC.REQUEST_ID_KEY));
  }

  @Test
  void testGenerateAndSetRequestId() {
    String requestId = RequestIdMDC.generateAndSetRequestId();

    assertNotNull(requestId);
    assertFalse(requestId.isEmpty());
    assertEquals(requestId, RequestIdMDC.getRequestId());
    assertEquals(requestId, MDC.get(RequestIdMDC.REQUEST_ID_KEY));
  }

  @Test
  void testClearRequestId() {
    RequestIdMDC.setRequestId("test-request-456");
    assertNotNull(RequestIdMDC.getRequestId());

    RequestIdMDC.clearRequestId();
    assertNull(RequestIdMDC.getRequestId());
  }

  @Test
  void testClearAll() {
    RequestIdMDC.setRequestId("test-request-789");
    MDC.put("other-key", "other-value");

    RequestIdMDC.clearAll();

    assertNull(RequestIdMDC.getRequestId());
    assertNull(MDC.get("other-key"));
  }

  @Test
  void testWithRequestId() {
    String originalRequestId = "original-123";
    String tempRequestId = "temp-456";

    RequestIdMDC.setRequestId(originalRequestId);

    RequestIdMDC.withRequestId(
        tempRequestId,
        () -> {
          assertEquals(tempRequestId, RequestIdMDC.getRequestId());
        });

    // Should restore original request ID
    assertEquals(originalRequestId, RequestIdMDC.getRequestId());
  }

  @Test
  void testWithRequestIdNoOriginal() {
    String tempRequestId = "temp-789";

    // No original request ID
    assertNull(RequestIdMDC.getRequestId());

    RequestIdMDC.withRequestId(
        tempRequestId,
        () -> {
          assertEquals(tempRequestId, RequestIdMDC.getRequestId());
        });

    // Should be cleared after execution
    assertNull(RequestIdMDC.getRequestId());
  }
}
