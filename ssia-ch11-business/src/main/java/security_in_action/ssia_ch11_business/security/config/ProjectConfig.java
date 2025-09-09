package security_in_action.ssia_ch11_business.security.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;
import security_in_action.ssia_ch11_business.security.authentication.properties.AuthServerProperties;
import security_in_action.ssia_ch11_business.security.authentication.provider.OtpAuthenticationProvider;
import security_in_action.ssia_ch11_business.security.authentication.provider.UsernamePasswordAuthenticationProvider;
import security_in_action.ssia_ch11_business.security.filter.InitialAuthenticationFilter;
import security_in_action.ssia_ch11_business.security.filter.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final InitialAuthenticationFilter initialAuthFilter;
    private final JwtAuthenticationFilter jwtFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthProvider;
    private final OtpAuthenticationProvider otpAuthProvider;
    private final AuthServerProperties authServerProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationManager(authenticationManager())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAt(initialAuthFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(usernamePasswordAuthProvider, otpAuthProvider));
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(authServerProperties.getBaseUrl())
                .build();
    }
}
