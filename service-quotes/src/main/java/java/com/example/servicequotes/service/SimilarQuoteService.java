package java.com.example.servicequotes.service;

import com.example.servicequotes.data.Quote;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SimilarQuoteService {
    Flux<Quote> getSimilarQuotes(Quote quote);

    Flux<Quote> getSimilarTags(List<String> tagsList);

    Flux<Quote> getSimilarAuthor(String author);
}
