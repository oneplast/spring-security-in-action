package security_in_action.ssia_ch15_auth.security.oauth2.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class OAuth2PasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private final String password;

    public OAuth2PasswordAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
