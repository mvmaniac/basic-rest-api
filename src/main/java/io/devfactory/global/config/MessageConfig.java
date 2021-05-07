package io.devfactory.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import static java.util.Locale.KOREAN;

@Configuration
public class MessageConfig {

  @Bean
  public LocaleResolver localeResolver() {
    final var sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(KOREAN);
    return sessionLocaleResolver;
  }

}
