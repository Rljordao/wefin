package com.wefin.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:Wefin}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mercado das Mil Sabedorias API")
                        .description("API para conversão de moeda no Reino SRM")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Reino Wefin")
                                .email("market@wefin.srm")))
                ;
    }
}