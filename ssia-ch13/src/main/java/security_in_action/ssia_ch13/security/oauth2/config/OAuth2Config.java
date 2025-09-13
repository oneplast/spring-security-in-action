package security_in_action.ssia_ch13.security.oauth2.config;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.PASSWORD;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Configuration
public class OAuth2Config {

/*
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
 */

    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client1 = RegisteredClient.withId("custom1")
                .clientId("client1")
                .clientSecret("secret1")
                .scopes(set -> set.addAll(List.of("read")))
                .authorizationGrantTypes(set -> set.addAll(List.of(AUTHORIZATION_CODE, REFRESH_TOKEN)))
                .redirectUri("http://localhost:8080/home")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        RegisteredClient client2 = RegisteredClient.withId("custom2")
                .clientId("client2")
                .clientSecret("secret2")
                .scopes(set -> set.addAll(List.of("read")))
                .authorizationGrantTypes(set -> set.addAll(List.of(
                        AUTHORIZATION_CODE, PASSWORD, REFRESH_TOKEN
                )))
                .redirectUri("http://localhost:8080/home")
                .build();

        return new InMemoryRegisteredClientRepository(List.of(client1, client2));
    }

/*
    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client = RegisteredClient.withId("custom")
                .clientId("client")
                .clientSecret("secret")
                .authorizationGrantTypes(set -> set.add(CLIENT_CREDENTIALS))
                .scopes(set -> set.add("info"))
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }
*/

    @Bean
    public OAuth2AuthorizationService OAuth2AuthService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService OAuth2ConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }
}
