package com.example.intergationtest;

import com.example.servicequotes.data.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorsQuotesControllerIntegrationTests {

    @Value("${base-url}")
    private String baseUrl;
    @Value("${server-port}")
    private int serverPort;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getRandomAuthorsTest() {
        webTestClient.get().uri(baseUrl + ":" + serverPort + "/api/v2/authors/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Author.class);
    }
}
