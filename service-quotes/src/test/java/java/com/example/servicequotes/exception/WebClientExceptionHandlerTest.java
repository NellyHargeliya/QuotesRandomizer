package java.com.example.servicequotes.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebClientExceptionHandlerTest {

    private WebClientExceptionHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new WebClientExceptionHandler();
    }

    @Test
    void shouldThrowBadRequestException() {
        ClientRequest request = mock(ClientRequest.class);
        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        ClientResponse response = ClientResponse.create(HttpStatus.BAD_REQUEST).build();

        when(exchangeFunction.exchange(request)).thenReturn(Mono.just(response));

        StepVerifier.create(handler.filter(request, exchangeFunction))
                .expectErrorMatches(error -> error.getMessage().contains("Bad request"))
                .verify();
    }

    @Test
    void shouldThrowInternalServerException() {
        ClientRequest request = mock(ClientRequest.class);
        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        ClientResponse response = ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build();

        when(exchangeFunction.exchange(request)).thenReturn(Mono.just(response));

        StepVerifier.create(handler.filter(request, exchangeFunction))
                .expectErrorMatches(error -> error.getMessage().contains("Internal Server Error"))
                .verify();
    }
}
