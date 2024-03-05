package com.example.quoterandomizer.controller;

import com.example.quoterandomizer.data.Author;
import com.example.quoterandomizer.data.Quote;
import com.example.quoterandomizer.service.QuotesServiceImpl;
import com.example.quoterandomizer.validator.RequestValidator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/quotes")
@Api("Quotes")
public class QuotesController {
    private final QuotesServiceImpl quoteService;
    private final RequestValidator requestValidator;

    @GetMapping
    public Mono<List<Quote>> getQuotes(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return quoteService.getQuotes(author, tagsList, page);
    }

    @GetMapping("/random/format")
    public String getRandomFormatQuotes(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) Integer minLength
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return quoteService.getRandomFormatQuotes(limit, tagsList, maxLength, minLength);
    }

    @GetMapping("/random")
    public Mono<List<Quote>> getRandomQuotes(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) Integer minLength
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return quoteService.getRandomQuotes(limit, tagsList, maxLength, minLength);
    }

    @GetMapping("/authors")
    public Mono<Author> getRandomAuthors() {
        return quoteService.getRandomAuthors();
    }
}
