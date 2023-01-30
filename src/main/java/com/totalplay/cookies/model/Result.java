package com.totalplay.cookies.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(title = "Resultado", description = "Especificación de los resultados.", required = true)
public class Result {

    @Schema(title = "Código", description = "Código del resultado.")
    private String code;

    @Schema(title = "Descripción", description = "Descripción del resultado.")
    private String description;

}
