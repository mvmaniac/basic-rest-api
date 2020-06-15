package io.devfactory.user.api;

import io.devfactory.post.domain.Post;
import io.devfactory.post.repository.PostRepository;
import io.devfactory.user.domain.User;
import io.devfactory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/jpa")
@RestController
public class JpaUserRestController {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable("id") Long id) {
    final User findUser = getFindUser(id);

    final EntityModel<User> entityModel = new EntityModel<>(findUser);
    final WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    entityModel.add(linkTo.withRel("all-users"));

    return entityModel;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    final User savedUser = userRepository.save(user);

    final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri()
        ;

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public void removeUser(@PathVariable("id") Long id) {
    userRepository.deleteById(id);
  }

  @GetMapping("/users/{id}/posts")
  public List<Post> retrieveAllPostsByUser(@PathVariable("id") Long id) {
    final User findUser = getFindUser(id);
    return findUser.getPosts();
  }

  @PostMapping("/users/{id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable("id") Long id, @RequestBody Post post) {
    final User findUser = getFindUser(id);

    post.changeUser(findUser);
    final Post savedPost = postRepository.save(post);

    final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedPost.getId())
        .toUri()
        ;

    return ResponseEntity.created(location).build();
  }

  private User getFindUser(@PathVariable("id") Long id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
