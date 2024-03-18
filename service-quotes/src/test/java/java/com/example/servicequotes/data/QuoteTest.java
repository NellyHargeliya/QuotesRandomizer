package java.com.example.servicequotes.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteTest {
    @Test
    void testIncrementLikes() {
        Quote quote = Quote.builder().likes(0).build();
        quote.incrementLikes();
        assertEquals(1, quote.getLikes());
    }

    @Test
    void testIncrementLikesWhenNull() {
        Quote quote = Quote.builder()
                .id("1")
                .content("Content")
                .author("Author")
                .build();

        quote.incrementLikes();

        assertEquals(1, quote.getLikes());
    }

    @Test
    void testIncrementLikesWhenNotNull() {
        Quote quote = Quote.builder().likes(0).build();
        quote.incrementLikes();
        assertEquals(1, quote.getLikes());
    }
}
