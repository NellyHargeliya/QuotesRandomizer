package java.com.example.servicequotes.service;

import com.example.servicequotes.configuration.ApiProperties;
import com.example.servicequotes.data.Author;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final ApiProperties apiProperties;
    private final WebClient webClient;

    public AuthorServiceImpl(ApiProperties apiProperties,
                             WebClient webClient) {

        this.apiProperties = apiProperties;
        this.webClient = webClient;
    }

    public Mono<Author> getRandomAuthors() {
        return webClient.get()
                .uri(apiProperties.getAuthorsEndpoint())
                .retrieve()
                .bodyToFlux(Author.class)
                .next();
    }
}
