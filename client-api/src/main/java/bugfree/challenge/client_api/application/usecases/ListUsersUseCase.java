package bugfree.challenge.client_api.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bugfree.challenge.domain.entities.User;
import bugfree.challenge.domain.repositories.UserRepository;

/** Use case for listing users with pagination */
@Service
@Transactional(readOnly = true)
public class ListUsersUseCase {

  private final UserRepository userRepository;

  public ListUsersUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ListUsersResponse execute(ListUsersRequest request) {
    List<User> users = userRepository.findAll(request.getPage(), request.getSize());
    long totalCount = userRepository.count();

    return new ListUsersResponse(users, totalCount, request.getPage(), request.getSize());
  }

  public List<User> executeActiveUsers() {
    return userRepository.findAllActive();
  }

  public static class ListUsersRequest {
    private final int page;
    private final int size;

    public ListUsersRequest(int page, int size) {
      this.page = Math.max(0, page); // Ensure page is not negative
      this.size = Math.min(Math.max(1, size), 100); // Ensure size is between 1 and 100
    }

    public int getPage() {
      return page;
    }

    public int getSize() {
      return size;
    }
  }

  public static class ListUsersResponse {
    private final List<User> users;
    private final long totalCount;
    private final int page;
    private final int size;
    private final int totalPages;

    public ListUsersResponse(List<User> users, long totalCount, int page, int size) {
      this.users = users;
      this.totalCount = totalCount;
      this.page = page;
      this.size = size;
      this.totalPages = (int) Math.ceil((double) totalCount / size);
    }

    public List<User> getUsers() {
      return users;
    }

    public long getTotalCount() {
      return totalCount;
    }

    public int getPage() {
      return page;
    }

    public int getSize() {
      return size;
    }

    public int getTotalPages() {
      return totalPages;
    }
  }
}
