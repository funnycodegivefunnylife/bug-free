package bugfree.challenge.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.infrastructure.persistence.entities.UserEntity;

/** Spring Data JPA repository for UserEntity */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);

  boolean existsByEmail(String email);

  List<UserEntity> findByStatus(UserStatus status);
}
