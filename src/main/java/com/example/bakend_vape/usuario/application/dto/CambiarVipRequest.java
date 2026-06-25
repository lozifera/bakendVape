package com.example.bakend_vape.usuario.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarVipRequest {

    @JsonProperty("es_vip")
    private Boolean esVip;

}
