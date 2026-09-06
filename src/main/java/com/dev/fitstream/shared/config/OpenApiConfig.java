package com.dev.fitstream.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fitStreamOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("FitStream API")
                .description("API modular monolith para gerenciamento de treinos e nutrição.")
                .version("v1.0.0"));
    }
}
