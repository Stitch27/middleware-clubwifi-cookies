package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Salida (Post)", description = "Parametros de salida del método post.", required = true)
public class ResponsePost {

    @Schema(title = "Resultado", description = "Parametros del resultado.")
    private Result result;

}
