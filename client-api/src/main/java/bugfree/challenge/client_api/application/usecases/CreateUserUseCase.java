package bugfree.challenge.client_api.application.usecases;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.domain.exceptions.UserAlreadyExistsException;
import bugfree.challenge.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Use case for creating a new user
 */
@Service
@Transactional
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    
    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User execute(CreateUserRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }
        
        // Create new user
        User user = new User(
            UUID.randomUUID().toString(),
            request.getEmail(),
            request.getFirstName(),
            request.getLastName(),
            request.getPassword(), // In real app, this should be hashed
            UserStatus.ACTIVE,
            LocalDateTime.now(),
            null
        );
        
        return userRepository.save(user);
    }
    
    public static class CreateUserRequest {
        private final String email;
        private final String firstName;
        private final String lastName;
        private final String password;
        
        public CreateUserRequest(String email, String firstName, String lastName, String password) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
        }
        
        public String getEmail() { return email; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getPassword() { return password; }
    }
}
