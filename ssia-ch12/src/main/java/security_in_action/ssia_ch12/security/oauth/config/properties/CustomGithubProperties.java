package security_in_action.ssia_ch12.security.oauth.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.oauth.client.registration.github")
public class CustomGithubProperties {

    private String clientId;
    private String clientSecret;
    private String clientName;
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;
    private String redirectUri;
}
