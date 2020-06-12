package io.devfactory.user.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ExceptionResponse {

  private String message;
  private LocalDateTime timeStamp;
  private String description;

  @Builder(builderMethodName = "create")
  private ExceptionResponse(String message, LocalDateTime timeStamp, String description) {
    this.message = message;
    this.timeStamp = timeStamp;
    this.description = description;
  }

}
