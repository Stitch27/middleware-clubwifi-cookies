package com.totalplay.cookies.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.core.env.Environment;
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

    @Autowired
    Environment environment;

    @GetMapping("/clubwifi/cookies/{mac}")
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
    public ResponseEntity<ResponseGet> getResponse(@Parameter(description = "Consultar mac") @PathVariable(value = "mac", required = true) String mac) {
        return cookieService.getInformation(mac);
    }

    @PostMapping("/clubwifi/cookies/")
    @Operation(summary = "Registrar / Actualizar cookies.",
            description = "Ingresar los parametros mac, code, address, subscriber, firstcookie y secondcookie para realizar un registro o actualización de cookies.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Petición realizada con éxito.", links = {
                            @Link(name = "Registrar / Actualizar cookies.", description = "application/json", operationId = "POST",
                                    parameters = {
                                            @LinkParameter(name = "code", expression = "xxZZ00@@--AA"),
                                            @LinkParameter(name = "address", expression = "10.118.12.57"),
                                            @LinkParameter(name = "subscriber", expression = "0102058895@tpz"),
                                            @LinkParameter(name = "firstCookie", expression = "tt9911..@@VV"),
                                            @LinkParameter(name = "secondCookie", expression = "kk++ll<<00ss"),
                                            @LinkParameter(name = "mac", expression = "a0:1b:c2:3d:e4:5f"),
                                    })
                    }, useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Ingresar toda la información solicitada.", links = {
                            @Link(name = "Registrar / Actualizar cookies.", description = "application/json", operationId = "POST",
                                    parameters = {
                                            @LinkParameter(name = "code", expression = "xxZZ00@@--AA"),
                                            @LinkParameter(name = "address", expression = "10.118.12.57"),
                                            @LinkParameter(name = "subscriber", expression = "0102058895@tpz"),
                                            @LinkParameter(name = "firstCookie", expression = "tt9911..@@VV"),
                                            @LinkParameter(name = "secondCookie", expression = "kk++ll<<00ss"),
                                            @LinkParameter(name = "mac", expression = "a0:1b:c2:3d:e4:5f"),
                                    })
                    }, useReturnTypeSchema = true)
            }
    )
    public ResponseEntity<ResponsePost> regUpdResp(@Parameter(description = "Parametros de entrada.") @RequestBody(required = false) RequestPost requestPost) {
        return cookieService.regUpdInf(requestPost);
    }

}
