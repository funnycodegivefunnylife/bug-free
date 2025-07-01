package bugfree.challenge.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import bugfree.challenge.domain.entities.idclasses.UserId;

/** User domain entity representing the core business concept of a user */
public class User {
  private final UserId id;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final String password;
  private final UserStatus status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public User(
      UserId id,
      String email,
      String firstName,
      String lastName,
      String password,
      UserStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = Objects.requireNonNull(id, "User ID cannot be null");
    this.email = Objects.requireNonNull(email, "Email cannot be null");
    this.firstName = Objects.requireNonNull(firstName, "First name cannot be null");
    this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null");
    this.password = Objects.requireNonNull(password, "Password cannot be null");
    this.status = Objects.requireNonNull(status, "Status cannot be null");
    this.createdAt = Objects.requireNonNull(createdAt, "Created at cannot be null");
    this.updatedAt = updatedAt;

    validateEmail(email);
  }

  private void validateEmail(String email) {
    if (!email.contains("@") || email.length() < 5) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }

  public User activate() {
    return new User(
        id,
        email,
        firstName,
        lastName,
        password,
        UserStatus.ACTIVE,
        createdAt,
        LocalDateTime.now());
  }

  public User deactivate() {
    return new User(
        id,
        email,
        firstName,
        lastName,
        password,
        UserStatus.INACTIVE,
        createdAt,
        LocalDateTime.now());
  }

  public User updateProfile(String firstName, String lastName) {
    return new User(
        id, email, firstName, lastName, password, status, createdAt, LocalDateTime.now());
  }

  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  // Getters
  public UserId getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public UserStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "User{"
        + "id='"
        + id
        + '\''
        + ", email='"
        + email
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", status="
        + status
        + ", createdAt="
        + createdAt
        + '}';
  }
}
