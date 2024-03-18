package java.com.example.servicequotes.service;

import com.example.servicequotes.configuration.ApiProperties;
import com.example.servicequotes.data.Quote;
import com.example.servicequotes.repository.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

 class QuotesServiceImplTest {

    private QuotesServiceImpl service;
    private WebClient webClient;
    private WebClient.RequestHeadersUriSpec requestSpec;
    @Mock
    private QuoteRepository quoteRepository;
    private WebClient.RequestHeadersSpec headersSpec;


    @BeforeEach
    public void setup() {
        ApiProperties apiProperties = mock(ApiProperties.class);
        webClient = mock(WebClient.class);
        requestSpec = mock(WebClient.RequestHeadersUriSpec.class);
        headersSpec = mock(WebClient.RequestHeadersSpec.class);
        service = new QuotesServiceImpl(apiProperties, webClient, quoteRepository);

        when(apiProperties.getQuoteRandomEndpoint()).thenReturn("/random");
        when(webClient.get()).thenReturn(requestSpec);
        when(requestSpec.uri(any(Function.class))).thenReturn(headersSpec);
    }

    @Test
    void testGetRandomQuotes() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(singletonList(getQuoteObject1())));

        StepVerifier.create(service.getRandomQuotes(1, List.of("tag1"), 100, 1))
                .expectNextCount(1)
                .verifyComplete();
    }

    Quote getQuoteObject1() {
        return Quote.builder()
                .id("1")
                .content("Content 1")
                .author("Author 1")
                .tags(Arrays.asList("tag1", "tag2"))
                .authorSlug("author-slug")
                .length(100)
                .dateAdded("2021-10-01")
                .dateModified("2021-10-02")
                .build();
    }
}