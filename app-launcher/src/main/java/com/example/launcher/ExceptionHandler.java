package com.example.launcher;

import com.example.platform.library.exceptions.UIMessageException;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {
  private static final String MESSAGE = "";

  @org.springframework.web.bind.annotation.ExceptionHandler(UIMessageException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ExceptionResponse handleMessageHandler(
      final UIMessageException exception, final HttpServletRequest request) {

    log.error("", exception);
    ExceptionResponse error = new ExceptionResponse();
    error.setMsg(exception.getMessage());
    return error;
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ExceptionResponse handleMessageHandler(
      final Exception exception, final HttpServletRequest request) {
    log.error("", exception);
    ExceptionResponse error = new ExceptionResponse();
    error.setMsg(MESSAGE);
    return error;
  }

  @Data
  @NoArgsConstructor
  private static class ExceptionResponse {
    private String msg;
  }
}
