package com.example.quoterandomizer.controller;

import com.example.quoterandomizer.data.Quote;
import com.example.quoterandomizer.service.QuotesServiceImpl;
import com.example.quoterandomizer.validator.RequestValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(QuotesController.class)
class QuotesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private QuotesServiceImpl quoteService;

    @Mock
    private RequestValidator requestValidator;

    @Test
    void testGetRandomQuotes() {
        List<Quote> mockQuotes = Arrays.asList(getQuoteObject1(), getQuoteObject2());
        List<String> tagsList = Arrays.asList("tag1", "tag2");

        when(requestValidator.processTags(anyString())).thenReturn(tagsList);
        when(quoteService.getRandomQuotes(anyInt(), anyList(), anyInt(), anyInt())).thenReturn(Mono.just(mockQuotes));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/quotes/random")
                        .queryParam("limit", "2")
                        .queryParam("tags", "tag1,tag2")
                        .queryParam("maxLength", "100")
                        .queryParam("minLength", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].author").isEqualTo("Author 1")
                .jsonPath("$.[0].quote").isEqualTo("Quote 1")
                .jsonPath("$.[1].author").isEqualTo("Author 2")
                .jsonPath("$.[1].quote").isEqualTo("Quote 2");

        verify(requestValidator, times(1)).processTags(anyString());
        verify(quoteService, times(1)).getRandomQuotes(anyInt(), anyList(), anyInt(), anyInt());
    }

    @Test
    public void testGetRandomFormatQuotes() {
        Quote quote = Quote.builder()
                ._id("1")
                .content("Content")
                .author("Author")
                .build();
        String expectedResponse = quote.toString();

        List<String> tagsList = Arrays.asList("tag1", "tag2");

        when(requestValidator.processTags(anyString())).thenReturn(tagsList);
        when(quoteService.getRandomFormatQuotes(anyInt(), anyList(), anyInt(), anyInt())).thenReturn(expectedResponse);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/quotes/random/format")
                        .queryParam("limit", "2")
                        .queryParam("tags", "tag1,tag2")
                        .queryParam("maxLength", "100")
                        .queryParam("minLength", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(expectedResponse);

        verify(requestValidator, times(1)).processTags(anyString());
        verify(quoteService, times(1)).getRandomFormatQuotes(anyInt(), anyList(), anyInt(), anyInt());
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

    Quote getQuoteObject2() {
        return Quote.builder()
                ._id("2")
                .content("Content 2")
                .author("Author 2")
                .tags(Arrays.asList("tag1", "tag2"))
                .authorSlug("author-slug2")
                .length(100)
                .dateAdded("2021-10-01")
                .dateModified("2021-10-02")
                .build();
    }
}
