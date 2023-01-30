package com.totalplay.cookies.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwagguerConfiguration {

    @Bean
    public OpenAPI getDefinition(){

        return new OpenAPI()
                .info(new Info()
                        .title("Middleware Club Wifi Cookies.")
                        .description("Proyecto Club Wifi para la gestión de cookies en dispositivos iOS.")
                        .version("1.0"))
                .addServersItem(new Server().url("https://apiservice-dev.sistemastp.com.mx").description("Dirección del servidor de Desarrollo."))
                .addServersItem(new Server().url("https://apiservice-qa.sistemastp.com.mx").description("Dirección del servidor de Calidad."))
                .addServersItem(new Server().url("https://apiservice.sistemastp.com.mx").description("Dirección del servidor de Producción."));
    }

}
