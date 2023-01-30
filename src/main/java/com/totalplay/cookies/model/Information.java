package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Información", description = "Información de la cookie.", required = true)
public class Information {

    @Schema(description = "Identificador del registro.", example = "1")
    private String identifier;
    @Schema(description = "Código del suscriptor.", example = "xxZZ00@@--AA")
    private String code;
    @Schema(description = "Dirección del suscriptor.", example = "10.118.12.57")
    private String address;
    @Schema(description = "Suscriptor de red.", example = "0102058895@tpz")
    private String subscriber;
    @Schema(description = "Primera cookie.", example = "tt9911..@@VV")
    private String firstCookie;
    @Schema(description = "Segunda cookie.", example = "kk++ll<<00ss")
    private String secondCookie;
    @Schema(description = "Fecha de registro.", example = "2023-01-29 03:27:49.000")
    private String creationDate;

}
