package security_in_action.ssia_ch11_business.security.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OtpAuthentication(Object principal, Object credentials,
                             Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
