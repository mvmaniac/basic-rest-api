package io.devfactory.global.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import io.devfactory.user.exception.UserNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest webRequest) {
    final ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest webRequest) {
    final ExceptionResponse exceptionResponse = ExceptionResponse.of(ex.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
  }

  @SuppressWarnings("NullableProblems")
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    final ExceptionResponse exceptionResponse = ExceptionResponse.of("Validation failed",
        LocalDateTime.now(), ex.getBindingResult().toString());

    return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
  }

}