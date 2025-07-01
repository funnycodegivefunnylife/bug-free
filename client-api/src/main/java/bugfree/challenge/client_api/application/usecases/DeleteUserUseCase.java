package bugfree.challenge.client_api.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.exceptions.UserNotFoundException;
import bugfree.challenge.domain.repositories.UserRepository;

/** Use case for deleting a user */
@Service
@Transactional
public class DeleteUserUseCase {

  private final UserRepository userRepository;

  public DeleteUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(String userId) {
    // Verify user exists before deletion
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    // In a real application, you might want to soft delete instead
    // by updating the user status to DELETED
    User deletedUser =
        new User(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPassword(),
            bugfree.challenge.domain.entities.UserStatus.DELETED,
            user.getCreatedAt(),
            java.time.LocalDateTime.now());

    userRepository.save(deletedUser);

    // For hard delete, use:
    // userRepository.deleteById(userId);
  }
}
