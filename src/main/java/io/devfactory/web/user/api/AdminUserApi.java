package io.devfactory.web.user.api;

import io.devfactory.web.user.domain.User;
import io.devfactory.web.user.domain.UserV2;
import io.devfactory.web.user.exception.UserNotFoundException;
import io.devfactory.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminUserApi {

  private final UserService userService;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/v1/users/{id}") // URL를 통한 버전관리
  public User retrieveUserV1(@PathVariable Long id) {
    final var findUser = userService.findUser(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    return findUser;
  }

  // @GetMapping(value = "/users/{id}/", params = "version=2") // RequestParam를 통한 버전관리
  // @GetMapping(value = "/users/{id}", headers = "x-api-version=2") // header를 통한 버전관리
  @GetMapping(value = "/users/{id}", produces = "application/vnd.api.v2+json") // MIME type를 통한 버전관리
  public UserV2 retrieveUserV2(@PathVariable Long id) {
    final var findUser = userService.findUser(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    return UserV2.of(findUser, "VIP");
  }

}
