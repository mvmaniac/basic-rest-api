package io.devfactory.user.service;

import io.devfactory.user.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static List<User> users = new ArrayList<>();
  private static int usersCount = 3;

  static {
    users.add(User.create().id(1L).name("Dev1").joinDate(LocalDateTime.now()).build());
    users.add(User.create().id(2L).name("Dev2").joinDate(LocalDateTime.now()).build());
    users.add(User.create().id(3L).name("Dev3").joinDate(LocalDateTime.now()).build());
  }

  public List<User> findAllUsers() {
    return users;
  }

  public User findUserById(Long id) {
    // @formatter:off
    return users.stream()
        .filter(user -> user.getId().equals(id))
        .findFirst()
        .orElse(null)
      ;
    // @formatter:on
  }

  public User saveUser(User user) {
    if (Objects.isNull(user.getId())) {
      user.changeId((long) ++usersCount);
    }

    users.add(user);
    return user;
  }

  public Long deleteUserById(Long id) {
    final boolean isRemove = users.removeIf(user -> user.getId().equals(id));
    return isRemove ? id : null;
  }

}
