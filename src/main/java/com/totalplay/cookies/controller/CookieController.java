package com.totalplay.cookies.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.model.ResponseGet;
import com.totalplay.cookies.model.RequestPost;
import io.swagger.v3.oas.annotations.Operation;
import com.totalplay.cookies.model.ResponsePost;
import io.swagger.v3.oas.annotations.links.Link;
import org.springframework.web.bind.annotation.*;
import com.totalplay.cookies.service.CookieService;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/middleware")
@Tag(name = "Cookies", description = "Métodos de Cookies Club Wifi.")
public class CookieController {

    @Autowired
    private CookieService cookieService;

    @GetMapping("/clubwifi/cookies")
    @Operation(summary = "Consultar registro de cookies.",
            description = "Ingresar una mac para poder retornar un registro de cookie.", responses = {
            @ApiResponse(responseCode = "200", description = "Petición realizada con éxito.", links = {
                    @Link(name = "Consultar cookies.", description = "application/json", operationId = "GET")
            }, useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Ingresar mac.", links = {
                    @Link(name = "Consultar cookies.", description = "application/json", operationId = "GET")
            }, useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "No se encontrarón coincidencias.", links = {
                    @Link(name = "Consultar cookies.", description = "application/json", operationId = "GET")
            }, useReturnTypeSchema = true)
    })
    public ResponseEntity<ResponseGet> getResponse(@Parameter(description = "Consultar mac") @RequestParam(name = "mac", required = false) String mac) {
        return cookieService.getInformation(mac);
    }

    @PostMapping("/clubwifi/cookies")
    @Operation(summary = "Registrar / Actualizar cookies.",
            description = "Ingresar los parametros mac, code, address, subscriber, firstcookie y secondcookie para realizar un registro o actualización de cookies.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Petición realizada con éxito.", links = {
                            @Link(name = "Registrar / Actualizar cookies.", description = "application/json", operationId = "POST",
                                    parameters = {
                                            @LinkParameter(name = "code", expression = "+YYLTsgg9+lfdPcv0duQ7g=="),
                                            @LinkParameter(name = "address", expression = "H6T+qCaUgiuMQxSZYDuj5A=="),
                                            @LinkParameter(name = "subscriber", expression = "bcpN9lHkOOsTUKQ4zeK62Q=="),
                                            @LinkParameter(name = "firstCookie", expression = "aCxLA5QmzEuKWX9oCSaqYA=="),
                                            @LinkParameter(name = "secondCookie", expression = "rUKjvj4EsR35gJgessMlVQ=="),
                                            @LinkParameter(name = "mac", expression = "rI1y82wmAZ5SGDcbatPdkA==")
                                    })
                    }, useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Excepción al tratar de desencriptar los parametros de entrada.", links = {
                            @Link(name = "Registrar / Actualizar cookies.", description = "application/json", operationId = "POST",
                                    parameters = {
                                            @LinkParameter(name = "code", expression = "+YYLTsgg9+lfdPcv0duQ7g=="),
                                            @LinkParameter(name = "address", expression = "H6T+qCaUgiuMQxSZYDuj5A=="),
                                            @LinkParameter(name = "subscriber", expression = "bcpN9lHkOOsTUKQ4zeK62Q=="),
                                            @LinkParameter(name = "firstCookie", expression = "aCxLA5QmzEuKWX9oCSaqYA=="),
                                            @LinkParameter(name = "secondCookie", expression = "rUKjvj4EsR35gJgessMlVQ=="),
                                            @LinkParameter(name = "mac", expression = "rI1y82wmAZ5SGDcbatPdkA==")
                                    })
                    }, useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", description = "Sin cambios realizados.", links = {
                            @Link(name = "Registrar / Actualizar cookies.", description = "application/json", operationId = "POST",
                                    parameters = {
                                            @LinkParameter(name = "code", expression = "+YYLTsgg9+lfdPcv0duQ7g=="),
                                            @LinkParameter(name = "address", expression = "H6T+qCaUgiuMQxSZYDuj5A=="),
                                            @LinkParameter(name = "subscriber", expression = "bcpN9lHkOOsTUKQ4zeK62Q=="),
                                            @LinkParameter(name = "firstCookie", expression = "aCxLA5QmzEuKWX9oCSaqYA=="),
                                            @LinkParameter(name = "secondCookie", expression = "rUKjvj4EsR35gJgessMlVQ=="),
                                            @LinkParameter(name = "mac", expression = "rI1y82wmAZ5SGDcbatPdkA==")
                                    })
                    }, useReturnTypeSchema = true)
            }
    )
    public ResponseEntity<ResponsePost> regUpdResp(@Parameter(description = "Parametros de entrada.") @RequestBody(required = false) RequestPost requestPost) {
        return cookieService.regUpdInf(requestPost);
    }

}
