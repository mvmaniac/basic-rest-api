package io.devfactory.web.user.repository;

import io.devfactory.web.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
