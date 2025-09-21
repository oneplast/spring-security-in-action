package security_in_action.ssia_ch18_resource.security.jwt.util;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("securityUtils")
public class SecurityUtils {

    public boolean isOwner(JwtAuthenticationToken auth, String workoutUser) {
        return workoutUser.equals(auth.getToken().getClaimAsString("preferred_username"));
    }
}
