package io.devfactory.web.user.api;

import io.devfactory.web.post.domain.Post;
import io.devfactory.web.post.repository.PostRepository;
import io.devfactory.web.user.domain.User;
import io.devfactory.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/jpa")
@RestController
public class JpaUserApi {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable Long id) {
    final var findUser = getFindUser(id);

    final EntityModel<User> entityModel = EntityModel.of(findUser);
    final WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(linkTo.withRel("all-users"));

    return ResponseEntity.ok(entityModel);
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    final var savedUser = userRepository.save(user);

    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> removeUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/users/{id}/posts")
  public ResponseEntity<List<Post>> retrieveAllPostsByUser(@PathVariable Long id) {
    final var findUser = getFindUser(id);
    return ResponseEntity.ok(findUser.getPosts());
  }

  @PostMapping("/users/{id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody Post post) {
    final var findUser = getFindUser(id);

    post.changeUser(findUser);
    final var savedPost = postRepository.save(post);

    final var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedPost.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  private User getFindUser(Long id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
