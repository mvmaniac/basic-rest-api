package io.devfactory.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.devfactory.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "tb_post")
@Entity
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  private String description;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder(builderMethodName = "create")
  private Post(String description, User user) {
    this.description = description;
    this.user = user;
  }

  public static Post of(String description, User user) {
    return new Post(description, user);
  }

  public void changeUser(User user) {
    this.user = user;
  }

}
