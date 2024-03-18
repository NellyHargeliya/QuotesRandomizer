package java.com.example.servicequotes.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@Document(collection = "quote")
public class Quote {
    @MongoId
    @JsonProperty("_id")
    @Field("_id")
    private String id;
    private String content;
    private String author;
    private List<String> tags;
    private String authorSlug;
    private Integer length;
    private String dateAdded;
    private String dateModified;
    private int likes;

    public void incrementLikes() {
        this.likes = Optional.ofNullable(this.likes).orElse(0) + 1;
    }
}
