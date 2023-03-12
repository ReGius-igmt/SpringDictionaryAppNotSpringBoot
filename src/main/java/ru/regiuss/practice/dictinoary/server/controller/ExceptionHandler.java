package ru.regiuss.practice.dictinoary.server.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.regiuss.practice.dictinoary.server.dto.ResponseDTO;

@RestControllerAdvice
@Log4j2
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseDTO<String> handleException(Exception ex, WebRequest request) {
        log.error("error", ex);
        return new ResponseDTO<>(ex.getMessage(), false);
    }

}
