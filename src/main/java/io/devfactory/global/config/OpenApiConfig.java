package io.devfactory.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private static final Contact DEFAULT_CONTACT = new Contact()
      .name("dev").email("dev@gmail.com").url("http://localhost:8080");

  private static final License DEFAULT_LICENSE = new License()
      .name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0");

  private static final Info DEFAULT_INFO = new Info()
      .title("Sample API title")
      .description("Sample REST API Service")
      .version("0.1")
      .termsOfService("tos")
      .license(DEFAULT_LICENSE)
      .contact(DEFAULT_CONTACT);

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(DEFAULT_INFO);
  }

  // http://localhost:8080/v3/api-docs
  // http://localhost:8080/swagger-ui.html
  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("rest-api")
        .pathsToMatch("/**")
        .build();
  }

}
