package com.example.quoterandomizer.service;

import com.example.quoterandomizer.configuration.ApiProperties;
import com.example.quoterandomizer.data.Author;
import com.example.quoterandomizer.data.Quote;
import com.example.quoterandomizer.model.QuoteResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuotesServiceImpl implements QuotesService {
    private final ApiProperties apiProperties;
    private final WebClient webClient;

    public QuotesServiceImpl(ApiProperties apiProperties, WebClient webClient) {
        this.apiProperties = apiProperties;
        this.webClient = webClient;
    }

    public String getRandomFormatQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength) {
        Mono<List<Quote>> randomQuotes = getRandomQuotes(limit, tags, maxLength, minLength);
        Mono<QuoteResponse> response = randomQuotes.flatMap(this::toRandomQuoteResponse);
        return response.block().toString();
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

    public Mono<Author> getRandomAuthors() {
        return webClient.get()
                .uri(apiProperties.getAuthorsEndpoint())
                .retrieve()
                .bodyToFlux(Author.class)
                .next();
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
