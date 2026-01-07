package io.devfactory.web.user.api;

import io.devfactory.web.user.domain.User;
import io.devfactory.web.user.exception.UserNotFoundException;
import io.devfactory.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class UserApi {

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> retrieveAllUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable Long id) {
    final var findUser = userService.findUser(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    // HATEOAS
    final EntityModel<User> entityModel = EntityModel.of(findUser);
    final WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(linkTo.withRel("all-users"));

    return ResponseEntity.ok(entityModel);
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    final var savedUser = userService.saveUser(user);

    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> removeUser(@PathVariable Long id) {
    final Long removeId = userService.deleteUser(id);

    if (isNull(removeId)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    return ResponseEntity.noContent().build();
  }

}
