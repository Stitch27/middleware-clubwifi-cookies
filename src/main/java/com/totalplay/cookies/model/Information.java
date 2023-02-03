package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Información", description = "Información de la cookie.", required = true)
public class Information {

    @Schema(description = "Identificador del registro.")
    private String identifier;
    @Schema(description = "Código del suscriptor.")
    private String code;
    @Schema(description = "Dirección del suscriptor.")
    private String address;
    @Schema(description = "Suscriptor de red.")
    private String subscriber;
    @Schema(description = "Primera cookie.")
    private String firstCookie;
    @Schema(description = "Segunda cookie.")
    private String secondCookie;
    @Schema(description = "Fecha de registro.")
    private String creationDate;

}
