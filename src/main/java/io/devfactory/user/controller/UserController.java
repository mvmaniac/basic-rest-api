package io.devfactory.user.controller;

import io.devfactory.user.domain.User;
import io.devfactory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public User retrieveUser(@PathVariable("id") Long id) {
    return userService.findUserById(id);
  }

  @PostMapping("/users")
  public User createUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

}
