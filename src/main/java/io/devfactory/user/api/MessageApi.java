package io.devfactory.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RequiredArgsConstructor
@RequestMapping("/messages")
@RestController
public class MessageApi {

  private final MessageSource messageSource;

  @GetMapping("/greeting")
  public String greeting(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    return messageSource.getMessage("greeting.message", null, locale);
  }

}
