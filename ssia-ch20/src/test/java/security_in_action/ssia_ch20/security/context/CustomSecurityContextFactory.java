package security_in_action.ssia_ch20.security.context;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import security_in_action.ssia_ch20.security.context.annotation.WithCustomUser;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomUser withCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(
                withCustomUser.username(), null, null);

        context.setAuthentication(a);

        return context;
    }
}
