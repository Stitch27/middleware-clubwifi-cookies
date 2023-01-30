package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Salida (Get)", description = "Parametros de salida del método get.", required = true)
public class ResponseGet {

    @Schema(title = "Resultado", description = "Parametros del resultado.")
    private Result result;
    @Schema(title = "Información", description = "Parametros de la información.")
    private Information information;

}
