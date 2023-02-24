package ru.regiuss.practice.dictinoary.server.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.regiuss.practice.dictinoary.server.dto.ResponseDTO;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseDTO<String> handleException(Exception ex, WebRequest request) {
        return new ResponseDTO<>(ex.getMessage(), false);
    }

}
