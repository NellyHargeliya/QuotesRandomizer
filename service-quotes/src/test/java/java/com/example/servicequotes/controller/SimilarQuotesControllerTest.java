package java.com.example.servicequotes.controller;

import com.example.servicequotes.data.Quote;
import com.example.servicequotes.service.SimilarQuoteService;
import com.example.servicequotes.validator.RequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(SimilarQuotesController.class)
class SimilarQuotesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SimilarQuoteService similarQuoteService;

    @MockBean
    private RequestValidator requestValidator;

    @Test
    void getSimilarQuotes() {
        Flux<Quote> similarQuoteFlux = Flux.just(getQuoteObject1(), getQuoteObject2());

        when(similarQuoteService.getSimilarQuotes(getQuoteObject1())).thenReturn(similarQuoteFlux);

        webTestClient.post()
                .uri("/api/v2/similar")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getQuoteObject1()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class)
                .hasSize(2);

        verify(similarQuoteService, times(1)).getSimilarQuotes(getQuoteObject2());
    }

    @Test
    void getSimilarTags() {
        List<String> tags = Arrays.asList("tag1", "tag2");
        Flux<Quote> similarQuoteFlux = Flux.just(getQuoteObject1(), getQuoteObject2());

        when(requestValidator.processTags("tag1,tag2")).thenReturn(tags);
        when(similarQuoteService.getSimilarTags(tags)).thenReturn(similarQuoteFlux);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v2/similar/tags")
                        .queryParam("tags", "tag1,tag2")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class)
                .hasSize(2);

        verify(requestValidator, times(1)).processTags("tag1,tag2");
        verify(similarQuoteService, times(1)).getSimilarTags(tags);
    }

    @Test
    void getSimilarAuthor() {
        String author = "author";
        Flux<Quote> similarQuoteFlux = Flux.just(getQuoteObject1(), getQuoteObject2());

        when(similarQuoteService.getSimilarAuthor(author)).thenReturn(similarQuoteFlux);

        webTestClient.get()
                .uri("/api/v2/quotes/author?author=" + author)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class)
                .hasSize(2);

        verify(similarQuoteService, times(1)).getSimilarAuthor(author);
    }
    Quote getQuoteObject1() {
        return Quote.builder()
                .id("1")
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
                .id("2")
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