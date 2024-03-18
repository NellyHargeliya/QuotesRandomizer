package java.com.example.servicequotes.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Author {
    private int count;
    private int totalCount;
    private int page;
    private int totalPages;
    private int lastItemIndex;
    private List<QuoteAuthor> results;
}
