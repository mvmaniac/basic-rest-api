package io.devfactory.user.exception;

import io.devfactory.global.error.ServiceRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends ServiceRuntimeException {

    public UserNotFoundException(String message) {
      super(message);
    }

}
