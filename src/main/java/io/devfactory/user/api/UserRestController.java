package io.devfactory.user.api;

import io.devfactory.user.domain.User;
import io.devfactory.user.exception.UserNotFoundException;
import io.devfactory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.TypeReferences.EntityModelType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class UserRestController {

  private final UserService userService;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable("id") Long id) {
    final User findUser = userService.findUserById(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    // HATEOAS
    final EntityModel<User> entityModel = new EntityModel<>(findUser);
    final WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(linkTo.withRel("all-users"));

    return entityModel;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    final User savedUser = userService.saveUser(user);

    final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri()
      ;

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public void removeUser(@PathVariable("id") Long id) {
    final Long removeId = userService.deleteUserById(id);

    if (Objects.isNull(removeId)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }
  }


}
