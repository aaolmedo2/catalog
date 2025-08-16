package com.banquito.core.loan.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.swagger-ui.url:http://localhost:80}")
    private String swaggerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catálogo de Préstamos API")
                        .version("1.0")
                        .description("API para gestión del catálogo de préstamos"))
                .servers(List.of(
                        new Server().url(swaggerUrl).description("Servidor de Producción")
                ));
    }
}
