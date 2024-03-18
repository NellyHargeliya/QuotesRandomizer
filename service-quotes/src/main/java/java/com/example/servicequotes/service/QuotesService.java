package java.com.example.servicequotes.service;

import com.example.servicequotes.data.Quote;
import reactor.core.publisher.Mono;

import java.util.List;

public interface QuotesService {
    Mono<String> getRandomFormatQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength);

    Mono<List<Quote>> getRandomQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength);

    Mono<List<Quote>> getQuotes(String author, List<String> tags, int page);

}
