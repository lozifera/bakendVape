package com.example.bakend_vape.usuario.domain.valueObject;

public class Password {

    private final String value;

    public Password(String value){

        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");

        }

        if (value.length() < 8) {

            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");

        }

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Password{" +
                "value='" + value + '\'' +
                '}';
    }

}
