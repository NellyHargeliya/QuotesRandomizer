package com.example.quoterandomizer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Quote {
    @JsonProperty("_id")
    private String _id;
    private String content;
    private String author;
    private List<String> tags;
    private String authorSlug;
    private Integer length;
    private String dateAdded;
    private String dateModified;
}
