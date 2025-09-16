package security_in_action.ssia_ch15_auth.security.oauth2.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import security_in_action.ssia_ch15_auth.security.oauth2.jwt.properties.JwtProperties;

@RequiredArgsConstructor
public class OAuth2PasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationManager manager;
    private final RegisteredClientRepository clientRepository;
    private final JwtProperties jwtProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2PasswordAuthenticationToken token = (OAuth2PasswordAuthenticationToken) authentication;

        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials()));

        RegisteredClient client = clientRepository.findByClientId("client");

        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(UTF_8));
        Instant nowInstant = Instant.now();
        Instant expInstant = nowInstant.plusSeconds(60 * 60 * 60);
        String jwt = Jwts.builder()
                .setClaims(Map.of("username", auth.getName()))
                .setIssuedAt(Date.from(nowInstant))
                .setExpiration(Date.from(expInstant))
                .signWith(key)
                .compact();

        return new OAuth2AccessTokenAuthenticationToken(client, auth,
                new OAuth2AccessToken(TokenType.BEARER, jwt, nowInstant, expInstant));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
