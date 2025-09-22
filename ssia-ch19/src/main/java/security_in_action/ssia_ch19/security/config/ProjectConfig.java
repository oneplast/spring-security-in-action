package security_in_action.ssia_ch19.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import security_in_action.ssia_ch19.security.webflux.CustomAuthorizationManager;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final CustomAuthorizationManager manager;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(auth -> auth
                        .anyExchange().access(manager))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        UserDetails u1 = User.withUsername("john")
                .password("12345")
                .authorities("ROLE_ADMIN", "read")
                .build();

        UserDetails u2 = User.withUsername("bill")
                .password("12345")
                .authorities("ROLE_USER", "read")
                .build();

        MapReactiveUserDetailsService uds = new MapReactiveUserDetailsService(u1, u2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
