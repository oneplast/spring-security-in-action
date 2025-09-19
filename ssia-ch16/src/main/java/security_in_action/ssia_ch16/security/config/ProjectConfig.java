package security_in_action.ssia_ch16.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService uds() {
        InMemoryUserDetailsManager service = new InMemoryUserDetailsManager();

        UserDetails u1 = User.withUsername("natalie")
                .password("12345")
//                .authorities("read")
                .roles("admin")
                .build();

        UserDetails u2 = User.withUsername("emma")
                .password("12345")
//                .authorities("write")
                .roles("manager")
                .build();

        service.createUser(u1);
        service.createUser(u2);

        return service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpHandler(
            DocumentPermissionEvaluator docPermissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expHandler = new DefaultMethodSecurityExpressionHandler();
        expHandler.setPermissionEvaluator(docPermissionEvaluator);

        return expHandler;
    }
}
