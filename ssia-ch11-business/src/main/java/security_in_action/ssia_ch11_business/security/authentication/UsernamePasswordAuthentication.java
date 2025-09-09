package security_in_action.ssia_ch11_business.security.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    // 인증 미완료 -> AuthenticationProvider 진입
    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    // 인증 완료
    public UsernamePasswordAuthentication(Object principal, Object credentials,
                                          Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
