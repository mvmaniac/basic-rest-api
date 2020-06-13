package io.devfactory.global.config;

import static java.util.Locale.KOREA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ApplicationConfig {

  @Bean
  public LocaleResolver localeResolver() {
    final SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(KOREA);
    return sessionLocaleResolver;
  }

}
