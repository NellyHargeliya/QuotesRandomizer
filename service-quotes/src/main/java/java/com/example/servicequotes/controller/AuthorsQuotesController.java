package java.com.example.servicequotes.controller;

import com.example.servicequotes.data.Author;
import com.example.servicequotes.service.AuthorService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/authors")
@Api("Quotes")
public class AuthorsQuotesController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public Mono<Author> getRandomAuthors() {
        return authorService.getRandomAuthors();
    }
}
