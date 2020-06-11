package io.devfactory.user.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

  private Long id;
  private String name;
  private LocalDateTime joinDate;

  @Builder(builderMethodName = "create")
  public User(Long id, String name, LocalDateTime joinDate) {
    this.id = id;
    this.name = name;
    this.joinDate = joinDate;
  }

  public void changeId(Long id) {
    this.id = id;
  }

}
