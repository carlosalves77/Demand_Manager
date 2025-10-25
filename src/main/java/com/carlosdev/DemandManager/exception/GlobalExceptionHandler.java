package com.carlosdev.DemandManager.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach( error ->
                erros.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(UserIdNotExistException.class)
    public ResponseEntity<Map<String, String>> handleUserIdNotExistsException(UserIdNotExistException ex) {
        Map<String, String> erros = new HashMap<>();

        erros.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable
            (HttpMessageNotReadableException ex) {

        Map<String, String> erros = new HashMap<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ifx && ifx.getTargetType().equals(UUID.class)) {
            erros.put("message", "Formato de UUID invalido");
        } else {
            erros.put("message", "corpo da requisição está malformado ou ilegível");
        }

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<Map<String, String>> handleExistsAlreadyEmail(UserEmailExistsException ex) {
        Map<String, String> erros = new HashMap<>();

        erros.put("message: ", "E-mail já existente");

        return ResponseEntity.badRequest().body(erros);
    }
}
