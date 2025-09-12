package security_in_action.ssia_ch13.security.oauth2.config;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.PASSWORD;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class OAuth2Config {

    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client = RegisteredClient.withId("custom")
                .clientId("client")
                .clientSecret("secret")
                .scopes(set -> set.addAll(List.of("read")))
                .authorizationGrantTypes(set -> set.addAll(List.of(PASSWORD, REFRESH_TOKEN)))
                .build();

        return new InMemoryRegisteredClientRepository(List.of(client));
    }
}
