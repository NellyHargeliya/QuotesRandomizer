package java.com.example.servicequotes.service;

import com.example.servicequotes.data.Author;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<Author> getRandomAuthors();
}
