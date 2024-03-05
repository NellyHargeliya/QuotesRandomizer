package com.example.quoterandomizer.data;

import lombok.Data;

import java.util.List;
@Data
public class Author {
    private int count;
    private int totalCount;
    private int page;
    private int totalPages;
    private int lastItemIndex;
    private List<QuoteAuthor> results;
}
