package security_in_action.ssia_ch12.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import security_in_action.ssia_ch12.security.oauth.config.GithubClientRegistration;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final GithubClientRegistration githubRegistration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRepository() {
        return new InMemoryClientRegistrationRepository(githubRegistration.regist());
    }
}
