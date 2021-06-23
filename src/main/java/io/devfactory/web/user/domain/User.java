package io.devfactory.web.user.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.devfactory.web.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@JsonIgnoreProperties({"password"})
//@JsonFilter("UserInfo")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "tb_user")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Schema(description = "사용자의 이름을 입력해 주세요.")
  @Size(min = 3, message = "name은 {min}글자 이상이여야 합니다.")
  private String name;

  @Schema(description = "사용자의 등록일을 입력해 주세요.")
  @Past
  @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime joinDate;

  @Schema(description = "사용자의 비밀번호를 입력해 주세요.")
  private String password;

  @Schema(description = "사용자의 주민번호를 입력해 주세요.")
  private String ssn;

  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

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

  public void updateId(Long id) {
    this.id = id;
  }

}
