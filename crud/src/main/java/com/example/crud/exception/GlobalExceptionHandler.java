package com.example.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // 🔴 VALIDACIONES
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String mensaje = messageSource.getMessage(error, locale);
            errores.put(error.getField(), mensaje);
        });

        return errores;
    }

    // 🔴 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarNoEncontrado(ResourceNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        String mensaje = messageSource.getMessage(ex.getCodigo(), null, locale);
        error.put("error", mensaje);

        return error;
    }

    // 🔴 ERROR GENERAL
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> manejarErroresGenerales(Exception ex) {

        Map<String, String> error = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        String mensaje = messageSource.getMessage("error.interno", null, locale);
        error.put("error", mensaje);

        return error;
    }
}