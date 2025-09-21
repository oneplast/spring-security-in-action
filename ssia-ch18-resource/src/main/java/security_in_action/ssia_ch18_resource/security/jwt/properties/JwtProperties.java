package security_in_action.ssia_ch18_resource.security.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {

    private Claim claim;
    private String jwkSetUri;

    @Getter
    @Setter
    public static class Claim {
        private String aud;
    }
}
