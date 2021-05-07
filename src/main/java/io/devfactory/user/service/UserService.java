package io.devfactory.user.service;

import io.devfactory.user.domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class UserService {

  private static final List<User> USERS = new ArrayList<>();
  private static final AtomicLong usersCount = new AtomicLong(3);

  static {
    USERS.add(User.of(1L, "Dev1", LocalDateTime.now(), "1234", "123456-78910"));
    USERS.add(User.of(2L, "Dev2", LocalDateTime.now(), "2234", "223456-78910"));
    USERS.add(User.of(3L, "Dev3", LocalDateTime.now(), "3234", "323456-78910"));
  }

  public List<User> findAllUsers() {
    return USERS;
  }

  public User findUser(Long id) {
    // @formatter:off
    return USERS.stream()
        .filter(user -> user.getId().equals(id))
        .findFirst()
        .orElse(null)
      ;
    // @formatter:on
  }

  public User saveUser(User user) {
    if (isNull(user.getId())) {
      user.updateId(usersCount.addAndGet(1));
    }

    USERS.add(user);
    return user;
  }

  public Long deleteUser(Long id) {
    final boolean isRemove = USERS.removeIf(user -> user.getId().equals(id));
    return isRemove ? id : null;
  }

}
