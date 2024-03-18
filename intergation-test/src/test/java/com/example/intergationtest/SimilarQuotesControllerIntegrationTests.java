package com.example.intergationtest;

import com.example.servicequotes.data.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimilarQuotesControllerIntegrationTests {

    @Value("${base-url}")
    private String baseUrl;
    @Value("${server-port}")
    private int serverPort;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getSimilarQuotesTest() {
        Quote quote = getQuoteObject();
        webTestClient.post().uri(baseUrl + ":" + serverPort + "/api/v2/similar")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(quote), Quote.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class);
    }

    @Test
    void getSimilarTagsTest() {
        webTestClient.get().uri(baseUrl + ":" + serverPort + "/api/v2/similar/tags?tags=tag1,tag2")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class);
    }

    @Test
    void getSimilarAuthorTest() {
        webTestClient.get().uri(baseUrl + ":" + serverPort + "/api/v2/similar/author?author=SomeAuthor")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class);
    }

    Quote getQuoteObject() {
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
