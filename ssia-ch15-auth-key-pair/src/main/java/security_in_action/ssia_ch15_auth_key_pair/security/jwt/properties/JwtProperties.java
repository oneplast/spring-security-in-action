package security_in_action.ssia_ch15_auth_key_pair.security.jwt.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {
    private String password;
    private String privateKey;
    private String alias;
}
