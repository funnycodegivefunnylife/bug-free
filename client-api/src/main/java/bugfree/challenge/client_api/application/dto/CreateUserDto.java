package bugfree.challenge.client_api.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Data Transfer Object for creating users */
public class CreateUserDto {

  @JsonProperty("email")
  @Email(message = "Email must be valid")
  @NotBlank(message = "Email is required")
  private String email;

  @JsonProperty("firstName")
  @NotBlank(message = "First name is required")
  @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
  private String firstName;

  @JsonProperty("lastName")
  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
  private String lastName;

  @JsonProperty("password")
  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
  private String password;

  // Default constructor for Jackson
  public CreateUserDto() {}

  public CreateUserDto(String email, String firstName, String lastName, String password) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }

  // Getters and setters
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
