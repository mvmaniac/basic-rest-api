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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @SuppressWarnings("NullableProblems")
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    final var errorResponse = ErrorResponse.of("Validation failed",
        LocalDateTime.now(), exception.getBindingResult().toString());
    return new ResponseEntity<>(errorResponse, BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest webRequest) {
    final var errorResponse = ErrorResponse.of(ex.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorResponse, NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleServerException(Exception exception, WebRequest webRequest) {
    final var errorResponse = ErrorResponse.of(exception.getMessage(),
        LocalDateTime.now(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
  }

}
