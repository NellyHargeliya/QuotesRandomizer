package java.com.example.servicequotes.controller;

import com.example.servicequotes.data.Quote;
import com.example.servicequotes.service.QuotesServiceImpl;
import com.example.servicequotes.validator.RequestValidator;
import io.swagger.annotations.Api;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/quotes")
@Api("Quotes")
public class QuotesController {
    private final QuotesServiceImpl quoteService;
    private final RequestValidator requestValidator;

    @GetMapping
    public Mono<List<Quote>> getQuotes(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false, defaultValue = "1") int page,
            @CookieValue(value = "isNewUser", defaultValue = "true") String isNewUser,
            HttpServletResponse response
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        boolean newUser = Boolean.parseBoolean(isNewUser);
        if (newUser) {
            response.addCookie(new Cookie("isNewUser", "false"));
            return quoteService.getTopQuotes().collectList();
        }
        return quoteService.getQuotes(author, tagsList, page);
    }

    @PostMapping("/{quoteId}/like")
    public Mono<ResponseEntity<Void>> likeQuote(@PathVariable String quoteId) {
        return quoteService.likeQuote(quoteId)
                .map(liked -> liked ? ResponseEntity.ok().build() : ResponseEntity.notFound().build());
    }

    @GetMapping("/random/format")
    public Mono<String> getRandomFormatQuotes(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) Integer minLength
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return quoteService.getRandomFormatQuotes(limit, tagsList, maxLength, minLength);
    }

    @GetMapping("/random")
    public Mono<List<Quote>> getRandomQuotes(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) Integer minLength
    ) {
        List<String> tagsList = requestValidator.processTags(tags);
        return quoteService.getRandomQuotes(limit, tagsList, maxLength, minLength);
    }
}
