package bugfree.challenge.domain.exceptions;

/** Exception thrown when attempting to create a user that already exists */
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String email) {
    super("User already exists with email: " + email);
  }
}
