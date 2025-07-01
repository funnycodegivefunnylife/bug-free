package bugfree.challenge.client_api.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.exceptions.UserNotFoundException;
import bugfree.challenge.domain.repositories.UserRepository;

/** Use case for retrieving a user by ID */
@Service
@Transactional(readOnly = true)
public class GetUserUseCase {

  private final UserRepository userRepository;

  public GetUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User execute(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  public User executeByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("email", email));
  }
}
