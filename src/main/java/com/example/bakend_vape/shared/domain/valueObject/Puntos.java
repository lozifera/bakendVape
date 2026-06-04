package com.example.bakend_vape.shared.domain.valueObject;

public record Puntos(Integer value) {

    public Puntos{

        if (value == null || value < 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser nulos o negativos");
        }

        if (value == 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser cero");
        }

    }

}
