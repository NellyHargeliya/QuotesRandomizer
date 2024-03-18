package java.com.example.servicequotes.model;

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
