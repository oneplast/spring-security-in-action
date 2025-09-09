package security_in_action.ssia_ch11_business.security.filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import security_in_action.ssia_ch11_business.security.authentication.OtpAuthentication;
import security_in_action.ssia_ch11_business.security.authentication.UsernamePasswordAuthentication;
import security_in_action.ssia_ch11_business.security.authentication.properties.JwtProperties;

@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectProvider<AuthenticationManager> managerProvider;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        AuthenticationManager manager = managerProvider.getObject();
        if (code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(a);
        } else {
            Authentication a = new OtpAuthentication(username, code);
            manager.authenticate(a);

            SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSigningKey().getBytes(UTF_8));

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
