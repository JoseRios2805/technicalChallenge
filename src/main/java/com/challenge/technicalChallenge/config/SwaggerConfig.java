package com.challenge.technicalChallenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge API")
                        .description("API para el reto técnico: cálculo con porcentaje y registro de historial")
                        .version("1.0.0"));
    }
}