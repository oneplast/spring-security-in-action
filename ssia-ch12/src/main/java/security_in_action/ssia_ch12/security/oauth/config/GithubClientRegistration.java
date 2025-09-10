package security_in_action.ssia_ch12.security.oauth.config;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import security_in_action.ssia_ch12.security.oauth.config.properties.CustomGithubProperties;

@Component
@RequiredArgsConstructor
public class GithubClientRegistration {

    private final CustomGithubProperties prop;

    public ClientRegistration regist() {
        return ClientRegistration.withRegistrationId("github")
                .clientId(prop.getClientId())
                .clientSecret(prop.getClientSecret())
                .clientName(prop.getClientName())
                .scope(List.of("read:user"))
                .authorizationUri(prop.getAuthorizationUri())
                .tokenUri(prop.getTokenUri())
                .userInfoUri(prop.getUserInfoUri())
                .userNameAttributeName("id")
                .authorizationGrantType(AUTHORIZATION_CODE)
                .redirectUri(prop.getRedirectUri())
                .build();
    }

    public ClientRegistration registByProvider() {
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId(prop.getClientId())
                .clientSecret(prop.getClientSecret())
                .build();
    }
}
