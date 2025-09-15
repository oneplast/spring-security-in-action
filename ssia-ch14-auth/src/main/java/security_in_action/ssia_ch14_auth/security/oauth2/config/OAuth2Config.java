package security_in_action.ssia_ch14_auth.security.oauth2.config;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Configuration
@RequiredArgsConstructor
public class OAuth2Config {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client = RegisteredClient.withId("client")
                .clientId("client")
                .clientSecret("secret")
                .scope("read")
                .authorizationGrantTypes(set -> set.addAll(List.of(AUTHORIZATION_CODE, REFRESH_TOKEN)))
                .redirectUri("http://localhost:8080/home")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        RegisteredClient resourceClient = RegisteredClient.withId("resourceClient")
                .clientId("resourceClient")
                .clientSecret("resourceSecret")
                .scope("introspect")
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .build();

        JdbcRegisteredClientRepository repo = new JdbcRegisteredClientRepository(jdbcTemplate);

        if (repo.findByClientId("client") == null) {
            repo.save(client);
        }

        if (repo.findByClientId("resourceClient") == null) {
            repo.save(resourceClient);
        }

        return repo;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthService() {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, clientRepository());
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2ConsentService() {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, clientRepository());
    }
}
