package java.com.example.servicequotes.service;

import com.example.servicequotes.configuration.ApiProperties;
import com.example.servicequotes.data.Quote;
import com.example.servicequotes.model.QuoteResponse;
import com.example.servicequotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuotesServiceImpl implements QuotesService {
    private final ApiProperties apiProperties;
    private final QuoteRepository quoteRepository;
    private final WebClient webClient;

    @Value("${quote.topLimit}")
    private int topLimit;

    public QuotesServiceImpl(ApiProperties apiProperties,
                             WebClient webClient,
                             QuoteRepository quoteRepository) {

        this.apiProperties = apiProperties;
        this.webClient = webClient;
        this.quoteRepository = quoteRepository;
    }

    public Mono<String> getRandomFormatQuotes(@Nullable Integer limit, @Nullable List<String> tags, @Nullable Integer maxLength, @Nullable Integer minLength) {
        Mono<List<Quote>> randomQuotes = getRandomQuotes(limit, tags, maxLength, minLength);
        Mono<QuoteResponse> response = randomQuotes.flatMap(this::toRandomQuoteResponse);
        return response.map(QuoteResponse::toString);
    }

    public Mono<List<Quote>> getRandomQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiProperties.getQuoteRandomEndpoint())
                        .queryParamIfPresent("limit", Optional.ofNullable(limit))
                        .queryParamIfPresent("tags", Optional.ofNullable(tags))
                        .queryParamIfPresent("maxLength", Optional.ofNullable(maxLength))
                        .queryParamIfPresent("minLength", Optional.ofNullable(minLength))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Quote>>() {
                });
    }

    public Mono<List<Quote>> getQuotes(String author, List<String> tags, int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiProperties.getQuoteEndpoint())
                        .queryParamIfPresent("author", Optional.ofNullable(author))
                        .queryParamIfPresent("tags", Optional.ofNullable(tags))
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Quote>>() {
                });
    }

    public Mono<Boolean> likeQuote(String quoteId) {
        return quoteRepository.findQuoteById(quoteId)
                .doOnSuccess(quote -> quote.incrementLikes())
                .flatMap(quoteRepository::save)
                .thenReturn(true)
                .switchIfEmpty(Mono.just(false));
    }

    public Flux<Quote> getTopQuotes() {
        return quoteRepository.findAllByOrderByLikesDesc(PageRequest.of(0, topLimit));
    }

    private Mono<QuoteResponse> toRandomQuoteResponse(List<Quote> quotes) {
        if (quotes.isEmpty()) {
            return Mono.empty();
        }
        Collections.shuffle(quotes);
        Quote quote = quotes.get(0);
        return Mono.just(QuoteResponse.builder()
                .content(quote.getContent())
                .author(quote.getAuthor())
                .build());
    }
}
