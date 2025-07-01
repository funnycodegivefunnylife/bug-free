package bugfree.challenge.infrastructure.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLRestriction;

import bugfree.challenge.domain.entities.UserStatus;
import bugfree.challenge.domain.entities.idclasses.UserId;
import bugfree.challenge.infrastructure.persistence.entities.converter.UserIdConverter;
import lombok.Getter;
import lombok.Setter;

/** JPA Entity for User table */
@Entity
@Table(name = "users")
@SQLRestriction("status <> 'DELETED'")
@Getter
@Setter
public class UserEntity {

  @Id
  @Convert(converter = UserIdConverter.class)
  private UserId id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    if (createdAt == null) {
      createdAt = now;
    }

    if (updatedAt == null) {
      updatedAt = now;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
