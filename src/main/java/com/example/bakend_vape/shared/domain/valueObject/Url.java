package com.example.bakend_vape.shared.domain.valueObject;

public record Url(String value) {
    public Url {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("La URL no puede ser nula o vacía");
        }
        // Validación básica de formato de URL
        if (!value.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$")) {
            throw new IllegalArgumentException("La URL no tiene un formato válido");
        }
    }
}
