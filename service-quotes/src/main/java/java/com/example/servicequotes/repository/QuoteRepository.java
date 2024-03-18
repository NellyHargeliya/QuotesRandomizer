package java.com.example.servicequotes.repository;

import com.example.servicequotes.data.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface QuoteRepository extends ReactiveCrudRepository<Quote, String> {
    Mono<Quote> findQuoteById(String id);

    Flux<Quote> findAllByOrderByLikesDesc(Pageable pageable);

    Flux<Quote> findByTagsIn(List<String> tags);

    Flux<Quote> findByAuthorContains(String author);
}
