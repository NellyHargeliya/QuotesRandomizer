package java.com.example.servicequotes.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class ApiProperties {
    private String baseUrl;
    private String quoteEndpoint;
    private String quoteRandomEndpoint;
    private String authorsEndpoint;
    private String searchQuotesEndpoint;
    private String searchAuthorsEndpoint;

}
