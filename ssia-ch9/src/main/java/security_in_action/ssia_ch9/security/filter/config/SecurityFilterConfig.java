package security_in_action.ssia_ch9.security.filter.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import security_in_action.ssia_ch9.security.filter.AuthenticationLoggingFilter;
import security_in_action.ssia_ch9.security.filter.RequestValidationFilter;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public RequestValidationFilter requestValidationFilter() {
        return new RequestValidationFilter();
    }

    @Bean
    public AuthenticationLoggingFilter authenticationLoggingFilter() {
        return new AuthenticationLoggingFilter();
    }

    @Bean
    public FilterRegistrationBean<RequestValidationFilter> disableSecurityValidationFilter(
            RequestValidationFilter filter) {
        return disableGlobalFilterChain(filter);
    }

    @Bean
    public FilterRegistrationBean<AuthenticationLoggingFilter> disableSecurityLoggingFilter(
            AuthenticationLoggingFilter filter) {
        return disableGlobalFilterChain(filter);
    }

    private <T extends Filter> FilterRegistrationBean<T> disableGlobalFilterChain(T filter) {
        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false); // 전역 서블릿 필터 체인에 필터 등록 비활성화
        return registration;
    }
}
