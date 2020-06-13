package io.devfactory.user.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties({"password"})
@JsonFilter("UserInfo")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User {

  private Long id;

  @Size(min = 3, message = "name은 {min}글자 이상이여야 합니다.")
  private String name;

  @Past
  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime joinDate;

  private String password;
  private String ssn;

  @Builder(builderMethodName = "create")
  protected User(Long id, String name, LocalDateTime joinDate, String password, String ssn) {
    this.id = id;
    this.name = name;
    this.joinDate = joinDate;
    this.password = password;
    this.ssn = ssn;
  }

  public static User of(Long id, String name, LocalDateTime joinDate, String password, String ssn) {
    return new User(id, name, joinDate, password, ssn);
  }

  public void changeId(Long id) {
    this.id = id;
  }

}
