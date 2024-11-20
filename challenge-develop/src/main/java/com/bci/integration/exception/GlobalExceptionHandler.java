package com.bci.integration.exception;

import com.bci.integration.controller.dto.UserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<Object> handleBadRequestException(BadRequestException exception){
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(getExceptionObjet(exception.getMessage()));
  }
  @ExceptionHandler({DuplicatedUserInfoException.class})
  public ResponseEntity<Object> handleDuplicatedUserInfoException(DuplicatedUserInfoException exception) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(getExceptionObjet(exception.getMessage()));
  }
  @ExceptionHandler({ServerErrorException.class})
  public ResponseEntity<Object> handleRuntimeException(ServerErrorException exception) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(getExceptionObjet(exception.getMessage()));
  }

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(getExceptionObjet(exception.getMessage()));
  }

  private UserErrorResponse getExceptionObjet(String errorMessage) {
    return new UserErrorResponse(errorMessage, System.currentTimeMillis());
  }

}
