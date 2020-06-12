package io.devfactory.user.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class User {

  private Long id;
  private String name;

  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
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
