package com.example.quoterandomizer.configuration;

import com.example.quoterandomizer.exception.WebClientExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final ApiProperties apiProperties;
    private final WebClientExceptionHandler webClientExceptionHandler;

    public WebClientConfig(ApiProperties apiProperties,
                           WebClientExceptionHandler webClientExceptionHandler) {
        this.apiProperties = apiProperties;
        this.webClientExceptionHandler = webClientExceptionHandler;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiProperties.getBaseUrl())
                .filter(webClientExceptionHandler)
                .build();
    }
}
