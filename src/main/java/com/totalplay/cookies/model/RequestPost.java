package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Entrada (Post)", description = "Parametros de entrada del método post.", required = true)
public class RequestPost {

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
    @Schema(description = "Mac del dispositivo en red.", example = "a0:1b:c2:3d:e4:5f")
    private String mac;

}
