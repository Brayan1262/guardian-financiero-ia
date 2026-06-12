package com.brayan.guardianfinanciero.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Guardián Financiero IA API")
                        .version("1.0.0")
                        .description("API REST para plataforma antifraude inteligente de transacciones financieras"));
    }
}
