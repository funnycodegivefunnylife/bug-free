package bugfree.challenge.domain.repositories;

import bugfree.challenge.domain.entities.User;
import java.util.List;
import java.util.Optional;

/**
 * User repository interface defining data access contracts
 */
public interface UserRepository {
    
    /**
     * Save a user entity
     */
    User save(User user);
    
    /**
     * Find user by ID
     */
    Optional<User> findById(String id);
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users with pagination
     */
    List<User> findAll(int page, int size);
    
    /**
     * Find all active users
     */
    List<User> findAllActive();
    
    /**
     * Delete user by ID
     */
    void deleteById(String id);
    
    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
    
    /**
     * Count total users
     */
    long count();
}
