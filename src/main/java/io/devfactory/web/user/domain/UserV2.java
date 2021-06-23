package io.devfactory.web.user.domain;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonFilter("UserInfoV2")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserV2 extends User {

  private String grade;

  private UserV2(Long id, String name, LocalDateTime joinDate, String password, String ssn,
      String grade) {
    super(id, name, joinDate, password, ssn);
    this.grade = grade;
  }

  public static UserV2 of(Long id, String name, LocalDateTime joinDate, String password, String ssn,
      String grade) {
    return new UserV2(id, name, joinDate, password, ssn, grade);
  }

  public static UserV2 of(User user, String grade) {
    return new UserV2(user.getId(), user.getName(), user.getJoinDate(), user.getPassword(),
        user.getSsn(), grade);
  }

}
