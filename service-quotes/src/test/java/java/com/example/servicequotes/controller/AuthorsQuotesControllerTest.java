package java.com.example.servicequotes.controller;

import com.example.servicequotes.data.Author;
import com.example.servicequotes.data.QuoteAuthor;
import com.example.servicequotes.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(AuthorsQuotesController.class)
class AuthorsQuotesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorService authorService;

    @Test
    void getRandomAuthors() {
        QuoteAuthor quoteAuthor = new QuoteAuthor();
        List<QuoteAuthor> results = Arrays.asList(quoteAuthor);
        Author author = Author.builder().count(1).results(results).build();

        when(authorService.getRandomAuthors()).thenReturn(Mono.just(author));

        webTestClient.get()
                .uri("/api/v2/authors/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.count").isEqualTo(author.getCount())
                .jsonPath("$.results[0].name").isEqualTo(quoteAuthor.getName());
    }
}

