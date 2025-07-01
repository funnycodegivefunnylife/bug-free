package bugfree.challenge.client_api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bugfree.challenge.client_api.application.dto.CreateUserDto;
import bugfree.challenge.client_api.application.dto.UpdateUserDto;
import bugfree.challenge.client_api.application.dto.UserDto;
import bugfree.challenge.client_api.application.mappers.UserMapper;
import bugfree.challenge.client_api.application.usecases.*;
import bugfree.challenge.domain.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

/** REST Controller for User management operations */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operations for managing users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;
  private final GetUserUseCase getUserUseCase;
  private final UpdateUserUseCase updateUserUseCase;
  private final DeleteUserUseCase deleteUserUseCase;
  private final ListUsersUseCase listUsersUseCase;
  private final UserMapper userMapper;

  public UserController(
      CreateUserUseCase createUserUseCase,
      GetUserUseCase getUserUseCase,
      UpdateUserUseCase updateUserUseCase,
      DeleteUserUseCase deleteUserUseCase,
      ListUsersUseCase listUsersUseCase,
      UserMapper userMapper) {
    this.createUserUseCase = createUserUseCase;
    this.getUserUseCase = getUserUseCase;
    this.updateUserUseCase = updateUserUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
    this.listUsersUseCase = listUsersUseCase;
    this.userMapper = userMapper;
  }

  @PostMapping
  @Operation(
      summary = "Create a new user",
      description = "Creates a new user with the provided information")
  @ApiResponse(responseCode = "201", description = "User created successfully")
  @ApiResponse(responseCode = "400", description = "Invalid input data")
  @ApiResponse(responseCode = "409", description = "User already exists")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
    CreateUserUseCase.CreateUserRequest request = userMapper.toCreateRequest(createUserDto);
    User user = createUserUseCase.execute(request);
    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get user by ID",
      description = "Retrieves a user by their unique identifier")
  @ApiResponse(responseCode = "200", description = "User found")
  @ApiResponse(responseCode = "404", description = "User not found")
  public ResponseEntity<UserDto> getUserById(
      @Parameter(description = "User ID") @PathVariable String id) {
    User user = getUserUseCase.execute(id);
    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.ok(userDto);
  }

  @GetMapping
  @Operation(summary = "List users", description = "Retrieves a paginated list of users")
  @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
  public ResponseEntity<ListUsersResponse> listUsers(
      @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

    ListUsersUseCase.ListUsersRequest request = new ListUsersUseCase.ListUsersRequest(page, size);
    ListUsersUseCase.ListUsersResponse response = listUsersUseCase.execute(request);

    List<UserDto> userDtos = response.getUsers().stream().map(userMapper::toDto).toList();

    ListUsersResponse apiResponse =
        new ListUsersResponse(
            userDtos,
            response.getTotalCount(),
            response.getPage(),
            response.getSize(),
            response.getTotalPages());

    return ResponseEntity.ok(apiResponse);
  }

  @GetMapping("/active")
  @Operation(summary = "List active users", description = "Retrieves all active users")
  @ApiResponse(responseCode = "200", description = "Active users retrieved successfully")
  public ResponseEntity<List<UserDto>> listActiveUsers() {
    List<User> users = listUsersUseCase.executeActiveUsers();
    List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();
    return ResponseEntity.ok(userDtos);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update user", description = "Updates user profile information")
  @ApiResponse(responseCode = "200", description = "User updated successfully")
  @ApiResponse(responseCode = "404", description = "User not found")
  @ApiResponse(responseCode = "400", description = "Invalid input data")
  public ResponseEntity<UserDto> updateUser(
      @Parameter(description = "User ID") @PathVariable String id,
      @Valid @RequestBody UpdateUserDto updateUserDto) {

    UpdateUserUseCase.UpdateUserRequest request = userMapper.toUpdateRequest(updateUserDto);
    User user = updateUserUseCase.execute(id, request);
    UserDto userDto = userMapper.toDto(user);
    return ResponseEntity.ok(userDto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete user", description = "Deletes a user by their ID")
  @ApiResponse(responseCode = "204", description = "User deleted successfully")
  @ApiResponse(responseCode = "404", description = "User not found")
  public ResponseEntity<Void> deleteUser(
      @Parameter(description = "User ID") @PathVariable String id) {
    deleteUserUseCase.execute(id);
    return ResponseEntity.noContent().build();
  }

  // Response DTO for list users endpoint
  @Getter
  public static class ListUsersResponse {
    private final List<UserDto> users;
    private final long totalCount;
    private final int page;
    private final int size;
    private final int totalPages;

    public ListUsersResponse(
        List<UserDto> users, long totalCount, int page, int size, int totalPages) {
      this.users = users;
      this.totalCount = totalCount;
      this.page = page;
      this.size = size;
      this.totalPages = totalPages;
    }
  }
}
