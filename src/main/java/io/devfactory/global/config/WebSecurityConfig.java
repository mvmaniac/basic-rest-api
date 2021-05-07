package io.devfactory.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // @formatter:off
    auth
      .inMemoryAuthentication()
        .withUser("dev")
          .password("{noop}1234")
          .roles("USER")
      ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
        .antMatchers("/h2-console/**")
          .permitAll()
        .anyRequest()
          .authenticated()
        .and()

      .csrf()
        .disable()

      .headers()
        .frameOptions()
          .sameOrigin()
        .and()

      .httpBasic()
      ;
    // @formatter:on
  }
}
