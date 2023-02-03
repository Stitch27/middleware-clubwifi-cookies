package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Entrada (Post)", description = "Parametros de entrada del método post.", required = true)
public class RequestPost {

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
    @Schema(description = "Mac del dispositivo en red.")
    private String mac;

}
