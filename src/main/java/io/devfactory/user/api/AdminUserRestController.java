package io.devfactory.user.api;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.devfactory.user.domain.User;
import io.devfactory.user.domain.UserV2;
import io.devfactory.user.exception.UserNotFoundException;
import io.devfactory.user.service.UserService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminUserRestController {

  private final UserService userService;

  @GetMapping("/users")
  public MappingJacksonValue retrieveAllUsers() {
    final List<User> findUsers = userService.findAllUsers();

    final SimpleFilterProvider filters = getSimpleFilterProvider("UserInfo");
    final MappingJacksonValue jacksonValue = new MappingJacksonValue(findUsers);
    jacksonValue.setFilters(filters);
    return jacksonValue;
  }

  @GetMapping("/v1/users/{id}") // URL를 통한 버전관리
  public MappingJacksonValue retrieveUserV1(@PathVariable("id") Long id) {
    final User findUser = userService.findUserById(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    final SimpleFilterProvider filters = getSimpleFilterProvider("UserInfo",
        "id", "name", "joinDate", "ssn");

    final MappingJacksonValue jacksonValue = new MappingJacksonValue(findUser);
    jacksonValue.setFilters(filters);
    return jacksonValue;
  }

//  @GetMapping(value = "/users/{id}/", params = "version=2") // RequestParam를 통한 버전관리
//  @GetMapping(value = "/users/{id}", headers = "x-api-version=2") // header를 통한 버전관리
  @GetMapping(value = "/users/{id}", produces = "application/vnd.api.v2+json") // MIME type를 통한 버전관리
  public MappingJacksonValue retrieveUserV2(@PathVariable("id") Long id) {
    final User findUser = userService.findUserById(id);

    if (Objects.isNull(findUser)) {
      throw new UserNotFoundException(String.format("User ID not found: %d", id));
    }

    final UserV2 userV2 = UserV2.of(findUser, "VIP");

    final SimpleFilterProvider filters = getSimpleFilterProvider("UserInfoV2",
        "id", "name", "joinDate", "grade");

    final MappingJacksonValue jacksonValue = new MappingJacksonValue(userV2);
    jacksonValue.setFilters(filters);
    return jacksonValue;
  }

  private SimpleFilterProvider getSimpleFilterProvider(String filterId, String... properties) {
    final SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter
        .filterOutAllExcept(properties);

    return new SimpleFilterProvider()
        .addFilter(filterId, propertyFilter);
  }

}
