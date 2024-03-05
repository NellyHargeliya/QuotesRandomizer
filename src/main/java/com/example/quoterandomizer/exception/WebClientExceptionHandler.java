package com.example.quoterandomizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class WebClientExceptionHandler implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return next.exchange(request)
                .flatMap(response -> {
                    if (response.statusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new Exception("Bad request: " + response.statusCode()));
                    } else if (response.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new Exception("Internal Server Error: " + response.statusCode()));
                    } else if (response.statusCode().isError()) {
                        return Mono.error(new Exception("Error: " + response.statusCode()));
                    }
                    return Mono.just(response);
                });
    }
}
