package io.devfactory.web.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.devfactory.web.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "tb_post")
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = IDENTITY)
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
