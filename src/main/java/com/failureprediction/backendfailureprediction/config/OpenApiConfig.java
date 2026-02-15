package com.failureprediction.backendfailureprediction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI failurePredictionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Failure Prediction & RCA System")
                        .description(
                                "Backend system to predict service failures using system metrics, " +
                                        "generate explainable risk scores, detect trends, trigger alerts, " +
                                        "and perform root cause analysis."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("")
                                .email("")
                        )
                        .license(new License().name("")));
    }
}