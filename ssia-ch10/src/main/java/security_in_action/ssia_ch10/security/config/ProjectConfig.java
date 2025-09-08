package security_in_action.ssia_ch10.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import security_in_action.ssia_ch10.security.filter.CsrfTokenLogger;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final CsrfTokenLogger csrfTokenLogger;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(csrfTokenLogger, CsrfFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll());

        return http.build();
    }
}
