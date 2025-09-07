package security_in_action.ssia_ch9.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import security_in_action.ssia_ch9.security.filter.config.StaticKeyAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

//    private final RequestValidationFilter requestValidationFilter;
//    private final AuthenticationLoggingFilter loggingFilter;
    private final StaticKeyAuthenticationFilter staticKeyFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(requestValidationFilter, BasicAuthenticationFilter.class)
//                .addFilterAfter(loggingFilter, BasicAuthenticationFilter.class)
                .addFilterAt(staticKeyFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll());

        return http.build();
    }
}
