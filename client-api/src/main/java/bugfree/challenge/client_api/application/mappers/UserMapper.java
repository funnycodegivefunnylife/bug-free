package bugfree.challenge.client_api.application.mappers;

import org.springframework.stereotype.Component;

import bugfree.challenge.client_api.application.dto.CreateUserDto;
import bugfree.challenge.client_api.application.dto.UpdateUserDto;
import bugfree.challenge.client_api.application.dto.UserDto;
import bugfree.challenge.client_api.application.usecases.CreateUserUseCase;
import bugfree.challenge.client_api.application.usecases.UpdateUserUseCase;
import bugfree.challenge.domain.entities.User;

/** Mapper for converting between DTOs and domain objects */
@Component
public class UserMapper {

  /** Convert User domain entity to UserDto */
  public UserDto toDto(User user) {
    return new UserDto(
        user.getId(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        user.getFullName(),
        user.getStatus().name(),
        user.getCreatedAt(),
        user.getUpdatedAt());
  }

  /** Convert CreateUserDto to CreateUserUseCase.CreateUserRequest */
  public CreateUserUseCase.CreateUserRequest toCreateRequest(CreateUserDto dto) {
    return new CreateUserUseCase.CreateUserRequest(
        dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.getPassword());
  }

  /** Convert UpdateUserDto to UpdateUserUseCase.UpdateUserRequest */
  public UpdateUserUseCase.UpdateUserRequest toUpdateRequest(UpdateUserDto dto) {
    return new UpdateUserUseCase.UpdateUserRequest(dto.getFirstName(), dto.getLastName());
  }
}
