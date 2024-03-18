package com.example.intergationtest;

import com.example.servicequotes.data.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuotesControllerIntegrationTests {

    @Value("${base-url}")
    private String baseUrl;
    @Value("${server-port}")
    private int serverPort;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getQuotesTest() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + ":" + serverPort)
                .path("/api/v2/quotes")
                .queryParam("author", "SomeAuthor")
                .queryParam("tags", "tag1,tag2")
                .build();
        webTestClient.get()
                .uri(uriComponents.toUri())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class);
    }

    @Test
    void likeQuoteTest() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + ":" + serverPort)
                .path("/api/v2/quotes/123/like")
                .build();
        webTestClient.post()
                .uri(uriComponents.toUri())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getRandomFormatQuotesTest() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + ":" + serverPort)
                .path("/api/v2/quotes/random/format")
                .build();
        webTestClient.get()
                .uri(uriComponents.toUri())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getRandomQuotesTest() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl + ":" + serverPort)
                .path("/api/v2/quotes/random")
                .build();
        webTestClient.get()
                .uri(uriComponents.toUri())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Quote.class);
    }
}