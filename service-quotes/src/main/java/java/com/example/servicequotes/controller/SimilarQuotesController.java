package java.com.example.servicequotes.controller;

import com.example.servicequotes.data.Quote;
import com.example.servicequotes.service.SimilarQuoteService;
import com.example.servicequotes.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/similar")
public class SimilarQuotesController {
    private final SimilarQuoteService similarQuoteService;
    private final RequestValidator requestValidator;

    @GetMapping
    public Flux<Quote> getSimilarQuotes(@RequestBody Quote quote) {
        return similarQuoteService.getSimilarQuotes(quote);
    }

    @GetMapping("/tags")
    public Flux<Quote> getSimilarTags(
            @RequestParam(required = false) String tags
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return similarQuoteService.getSimilarTags(tagsList);
    }

    @GetMapping("/author")
    public Flux<Quote> getSimilarAuthor(
            @RequestParam(required = false) String author
    ) {
        return similarQuoteService.getSimilarAuthor(author);
    }
}
