package java.com.example.servicequotes.repository;

import com.example.servicequotes.data.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
class QuoteRepositoryTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    void findQuoteByIdTest() {
        Quote quote = Quote.builder()
                .id("1")
                .content("Content")
                .author("Author")
                .build();
        quote.setId("test");
        quoteRepository.save(quote).block();

        Mono<Quote> found = quoteRepository.findQuoteById("test");

        StepVerifier
                .create(found)
                .expectNextMatches(saved -> saved.getId().equals(quote.getId()))
                .verifyComplete();
    }

    @Test
    void findByTagsInTest() {
        Quote quote = Quote.builder()
                .id("1")
                .content("Content")
                .author("Author")
                .build();
        quote.setId("test");
        quote.setTags(Collections.singletonList("tag1"));
        quoteRepository.save(quote).block();

        List<String> tags = Collections.singletonList("tag1");
        StepVerifier
                .create(quoteRepository.findByTagsIn(tags))
                .expectNextMatches(saved -> saved.getTags().equals(tags))
                .verifyComplete();
    }

}


