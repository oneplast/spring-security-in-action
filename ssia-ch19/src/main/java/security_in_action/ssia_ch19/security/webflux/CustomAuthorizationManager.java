package security_in_action.ssia_ch19.security.webflux;

import java.time.LocalTime;
import java.util.function.Function;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> auth, AuthorizationContext context) {
        String path = getRequestPath(context);
        boolean restrictedTime = LocalTime.now().isAfter(LocalTime.NOON);

        if (path.equals("/hello")) {
            return auth.map(isAdmin())
                    .map(authenticated -> authenticated && restrictedTime)
                    .map(AuthorizationDecision::new);
        }

        return Mono.just(new AuthorizationDecision(false));
    }

    private String getRequestPath(AuthorizationContext context) {
        return context.getExchange().getRequest().getPath().toString();
    }

    private Function<Authentication, Boolean> isAdmin() {
        return auth -> auth.getAuthorities().stream()
                .anyMatch(str -> str.getAuthority().equals("ROLE_ADMIN"));
    }
}
