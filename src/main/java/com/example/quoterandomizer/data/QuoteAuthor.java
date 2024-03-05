package com.example.quoterandomizer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuoteAuthor {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String bio;
    private String description;
    private String link;
    private int quoteCount;
    private String slug;
    private String dateAdded;
    private String dateModified;
}
