package java.com.example.servicequotes.service;

import com.example.servicequotes.configuration.ApiProperties;
import com.example.servicequotes.data.Author;
import com.example.servicequotes.data.Quote;
import com.example.servicequotes.repository.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SimilarQuotesServiceImpl implements SimilarQuoteService {
    private final ApiProperties apiProperties;
    private final QuoteRepository quoteRepository;
    private final WebClient webClient;

    public SimilarQuotesServiceImpl(ApiProperties apiProperties,
                                    WebClient webClient,
                                    QuoteRepository quoteRepository) {

        this.apiProperties = apiProperties;
        this.webClient = webClient;
        this.quoteRepository = quoteRepository;
    }

    public Flux<Quote> getSimilarQuotes(Quote quote) {
        List<String> tags = quote.getTags();
        return getSimilarTags(tags);
    }

    public Flux<Quote> getSimilarTags(List<String> tags) {
        return quoteRepository.findByTagsIn(tags);
    }

    public Flux<Quote> getSimilarAuthor(String author) {
        return quoteRepository.findByAuthorContains(author);
    }

    public Mono<Author> getRandomAuthors() {
        return webClient.get()
                .uri(apiProperties.getAuthorsEndpoint())
                .retrieve()
                .bodyToFlux(Author.class)
                .next();
    }
}
