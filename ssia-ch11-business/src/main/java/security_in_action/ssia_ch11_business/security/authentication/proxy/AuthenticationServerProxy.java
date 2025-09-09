package security_in_action.ssia_ch11_business.security.authentication.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import security_in_action.ssia_ch11_business.security.domain.User;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {

    private final ObjectProvider<WebClient> webClientProvider;

    public void sendAuth(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        webClientProvider.getObject().post()
                .uri("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    public boolean sendOtp(String username, String code) {
        User user = new User();
        user.setUsername(username);
        user.setCode(code);

        return Boolean.TRUE.equals(webClientProvider.getObject().post()
                .uri("/otp/check")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .toBodilessEntity()
                .map(resp -> resp.getStatusCode().equals(HttpStatus.OK))
                .block());
    }
}
