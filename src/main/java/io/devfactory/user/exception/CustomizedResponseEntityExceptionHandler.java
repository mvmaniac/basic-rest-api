package io.devfactory.user.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest webRequest) {
    final ExceptionResponse exceptionResponse = exceptionResponseOf(ex.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest webRequest) {
    final ExceptionResponse exceptionResponse = exceptionResponseOf(ex.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
  }

  private ExceptionResponse exceptionResponseOf(String message, LocalDateTime timeStamp,
      String description) {
    return ExceptionResponse.create()
        .message(message)
        .timeStamp(timeStamp)
        .description(description)
        .build();
  }

}
