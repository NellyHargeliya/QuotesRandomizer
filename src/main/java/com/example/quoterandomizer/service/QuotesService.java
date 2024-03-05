package com.example.quoterandomizer.service;

import com.example.quoterandomizer.data.Author;
import com.example.quoterandomizer.data.Quote;
import com.example.quoterandomizer.model.QuoteResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface QuotesService {
    String getRandomFormatQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength);
    Mono<List<Quote>> getRandomQuotes(Integer limit, List<String> tags, Integer maxLength, Integer minLength);
    Mono<List<Quote>> getQuotes(String author, List<String> tags, int page);

    Mono<Author> getRandomAuthors();
}
