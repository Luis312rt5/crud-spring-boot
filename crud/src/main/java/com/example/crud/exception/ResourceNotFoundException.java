package com.example.crud.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final String codigo;

    public ResourceNotFoundException(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}