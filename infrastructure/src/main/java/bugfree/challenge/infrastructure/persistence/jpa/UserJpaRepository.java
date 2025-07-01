package bugfree.challenge.infrastructure.persistence.jpa;

import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for UserEntity
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    
    Optional<UserEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<UserEntity> findByStatus(UserStatus status);
}
