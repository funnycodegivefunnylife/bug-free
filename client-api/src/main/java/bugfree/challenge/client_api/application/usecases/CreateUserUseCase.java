package bugfree.challenge.client_api.application.usecases;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.domain.entities.idclasses.UserId;
import bugfree.challenge.domain.exceptions.UserAlreadyExistsException;
import bugfree.challenge.domain.repositories.UserRepository;
import lombok.Getter;

/** Use case for creating a new user */
@Service
@Transactional
public class CreateUserUseCase {

  private final UserRepository userRepository;

  public CreateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(CreateUserRequest request) {
    // Check if user already exists
    if (userRepository.existsByEmail(request.email())) {
      throw new UserAlreadyExistsException(request.email());
    }

    // Create new user
    User user =
        new User(
            UserId.next(),
            request.email(),
            request.firstName(),
            request.lastName(),
            request.password(), // In real app, this should be hashed
            UserStatus.ACTIVE,
            LocalDateTime.now(),
            null);

    return userRepository.save(user);
  }

  @Getter
  public record CreateUserRequest(
      String email, String firstName, String lastName, String password) {}
}
