package com.example.quoterandomizer.validator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class RequestValidator {
    public List<String> processTags(String tags) {
        return tags != null ? Arrays.asList(tags.split("[,|]")) : null;
    }
}
