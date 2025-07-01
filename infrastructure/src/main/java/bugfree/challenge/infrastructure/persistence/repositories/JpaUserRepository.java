package bugfree.challenge.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.domain.repositories.UserRepository;
import bugfree.challenge.infrastructure.persistence.entities.UserEntity;
import bugfree.challenge.infrastructure.persistence.jpa.UserJpaRepository;

/** JPA implementation of UserRepository */
@Repository
public class JpaUserRepository implements UserRepository {

  private final UserJpaRepository jpaRepository;

  public JpaUserRepository(UserJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public User save(User user) {
    UserEntity entity = toEntity(user);
    UserEntity savedEntity = jpaRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public Optional<User> findById(String id) {
    return jpaRepository.findById(id).map(this::toDomain);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return jpaRepository.findByEmail(email).map(this::toDomain);
  }

  @Override
  public List<User> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<UserEntity> entityPage = jpaRepository.findAll(pageable);
    return entityPage.getContent().stream().map(this::toDomain).collect(Collectors.toList());
  }

  @Override
  public List<User> findAllActive() {
    return jpaRepository.findByStatus(UserStatus.ACTIVE).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(String id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }

  @Override
  public long count() {
    return jpaRepository.count();
  }

  // Mapping methods
  private UserEntity toEntity(User user) {
    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setEmail(user.getEmail());
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setPassword(user.getPassword());
    entity.setStatus(user.getStatus());
    entity.setCreatedAt(user.getCreatedAt());
    entity.setUpdatedAt(user.getUpdatedAt());
    return entity;
  }

  private User toDomain(UserEntity entity) {
    return new User(
        entity.getId(),
        entity.getEmail(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getPassword(),
        entity.getStatus(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }
}
