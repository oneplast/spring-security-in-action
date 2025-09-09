package security_in_action.ssia_ch11_business.security.filter.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import security_in_action.ssia_ch11_business.security.authentication.properties.JwtProperties;
import security_in_action.ssia_ch11_business.security.filter.InitialAuthenticationFilter;
import security_in_action.ssia_ch11_business.security.filter.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private final JwtProperties jwtProperties;
    private final ObjectProvider<AuthenticationManager> managerProvider;

    @Bean
    public InitialAuthenticationFilter initialAuthFilter() {
        return new InitialAuthenticationFilter(managerProvider, jwtProperties);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProperties);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> enableJwtFilter(JwtAuthenticationFilter filter) {
        return globalEnable(filter);
    }

    @Bean
    public FilterRegistrationBean<InitialAuthenticationFilter> enableInitialFilter(InitialAuthenticationFilter filter) {
        return globalEnable(filter);
    }

    private <T extends Filter> FilterRegistrationBean<T> globalEnable(T filter) {
        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
