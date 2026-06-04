package com.example.bakend_vape.shared.domain.valueObject;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null) {
            throw new IllegalArgumentException("El valor no puede ser nulo");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
    }
}
