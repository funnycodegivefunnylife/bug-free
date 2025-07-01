package bugfree.challenge.client_api.controller.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bugfree.challenge.domain.exceptions.UserAlreadyExistsException;
import bugfree.challenge.domain.exceptions.UserNotFoundException;

/** Global exception handler for the application */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    ErrorResponse error = new ErrorResponse("USER_NOT_FOUND", ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
      UserAlreadyExistsException ex) {
    ErrorResponse error =
        new ErrorResponse("USER_ALREADY_EXISTS", ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    ValidationErrorResponse errorResponse =
        new ValidationErrorResponse(
            "VALIDATION_ERROR",
            "Validation failed for one or more fields",
            errors,
            LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorResponse error =
        new ErrorResponse("INVALID_ARGUMENT", ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(
            "INTERNAL_SERVER_ERROR", "An unexpected error occurred", LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  // Error response classes
  public static class ErrorResponse {
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(String code, String message, LocalDateTime timestamp) {
      this.code = code;
      this.message = message;
      this.timestamp = timestamp;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }
  }

  public static class ValidationErrorResponse extends ErrorResponse {
    private final Map<String, String> fieldErrors;

    public ValidationErrorResponse(
        String code, String message, Map<String, String> fieldErrors, LocalDateTime timestamp) {
      super(code, message, timestamp);
      this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
      return fieldErrors;
    }
  }
}
