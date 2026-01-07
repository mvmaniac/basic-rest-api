package io.devfactory.global.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    // @formatter:off
    final var user = User
      .withUsername("dev")
      .password("{noop}1234")
      .roles("USER")
      .build();
    // @formatter:on
    return new InMemoryUserDetailsManager(user);
  }

  @Order(0)
  @Bean
  public SecurityFilterChain resourceChain(HttpSecurity http) {
    // @formatter:off
    return http
      .securityMatcher("/docs/**")
      .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
      .requestCache(RequestCacheConfigurer::disable)
      .securityContext(AbstractHttpConfigurer::disable)
      .sessionManagement(AbstractHttpConfigurer::disable)
      .build();
    // @formatter:on
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    // @formatter:off
    return http
        .authorizeHttpRequests(authorize -> authorize
          .requestMatchers(PathRequest.toH2Console())
            .permitAll()
          .anyRequest()
            .authenticated())
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .httpBasic(withDefaults())
        .build();
    // @formatter:on
  }

}
