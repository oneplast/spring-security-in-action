package security_in_action.ssia_ch19_method;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

@SpringBootApplication
@EnableReactiveMethodSecurity
public class SsiaCh19MethodApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsiaCh19MethodApplication.class, args);
    }

}
