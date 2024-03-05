package com.example.quoterandomizer.service;

import com.example.quoterandomizer.configuration.ApiProperties;
import com.example.quoterandomizer.data.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuotesServiceImplTest {

    private QuotesServiceImpl service;
    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec requestSpec;
    private WebClient.RequestHeadersSpec headersSpec;

    @BeforeEach
    public void setup() {
        ApiProperties apiProperties = mock(ApiProperties.class);
        webClient = mock(WebClient.class);
        requestSpec = mock(WebClient.RequestHeadersUriSpec.class);
        headersSpec = mock(WebClient.RequestHeadersSpec.class);
        service = new QuotesServiceImpl(apiProperties, webClient);

        when(apiProperties.getQuoteRandomEndpoint()).thenReturn("/random");
        when(webClient.get()).thenReturn(requestSpec);
        when(requestSpec.uri(any(Function.class))).thenReturn(headersSpec);
    }

    @Test
    public void testGetRandomQuotes() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(singletonList(getQuoteObject1())));

        StepVerifier.create(service.getRandomQuotes(1, List.of("tag1"), 100, 1))
                .expectNextCount(1)
                .verifyComplete();
    }

    Quote getQuoteObject1() {
        return Quote.builder()
                ._id("1")
                .content("Content 1")
                .author("Author 1")
                .tags(Arrays.asList("tag1", "tag2"))
                .authorSlug("author-slug")
                .length(100)
                .dateAdded("2021-10-01")
                .dateModified("2021-10-02")
                .build();
    }
}