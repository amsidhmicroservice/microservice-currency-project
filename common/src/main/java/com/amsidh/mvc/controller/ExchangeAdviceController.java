package com.amsidh.mvc.controller;

import com.amsidh.mvc.exception.ErrorMessage;
import com.amsidh.mvc.exception.MyCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExchangeAdviceController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
                .description(request.getDescription(false))
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .build();
        messageLog(message);
        return message;
    }

    @ExceptionHandler(MyCustomException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(MyCustomException ex, WebRequest request) {

        ErrorMessage message = ErrorMessage.builder()
                .description(request.getDescription(false))
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .build();
        messageLog(message);
        return message;
    }

    @SneakyThrows
    private void messageLog(ErrorMessage message) {
        log.error(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(message));
    }

}
