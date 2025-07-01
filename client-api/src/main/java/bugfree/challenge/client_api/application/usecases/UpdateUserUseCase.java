package bugfree.challenge.client_api.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.exceptions.UserNotFoundException;
import bugfree.challenge.domain.repositories.UserRepository;

/** Use case for updating user profile information */
@Service
@Transactional
public class UpdateUserUseCase {

  private final UserRepository userRepository;

  public UpdateUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(String userId, UpdateUserRequest request) {
    User existingUser =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    User updatedUser = existingUser.updateProfile(request.getFirstName(), request.getLastName());

    return userRepository.save(updatedUser);
  }

  public static class UpdateUserRequest {
    private final String firstName;
    private final String lastName;

    public UpdateUserRequest(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }
  }
}
