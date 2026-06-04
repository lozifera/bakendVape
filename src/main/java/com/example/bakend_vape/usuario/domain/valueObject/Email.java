package com.example.bakend_vape.usuario.domain.valueObject;

import java.util.regex.Pattern;

public class Email {

    private String value;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Email(String value) {

        if (value == null || value.isBlank()){
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("El email no tiene un formato válido");
        }

        this.value = value.toLowerCase().trim();

    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }


}
