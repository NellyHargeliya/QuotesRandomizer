package com.example.quoterandomizer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class QuoteResponse {
    private String content;
    private String author;

    @Override
    public String toString() {
        return "\"" + content + "\"" +
                ", " + author;
    }
}
