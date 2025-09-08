package security_in_action.ssia_ch10.security.filter.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import security_in_action.ssia_ch10.security.filter.CsrfTokenLogger;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public CsrfTokenLogger csrfTokenLogger() {
        return new CsrfTokenLogger();
    }

    @Bean
    public FilterRegistrationBean<CsrfTokenLogger> disableCsrfLogger(CsrfTokenLogger filter) {
        return disableGlobal(filter);
    }

    private <T extends Filter> FilterRegistrationBean<T> disableGlobal(T filter) {
        FilterRegistrationBean<T> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
