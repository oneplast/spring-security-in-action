package security_in_action.ssia_ch16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SsiaCh16Application {

    public static void main(String[] args) {
        SpringApplication.run(SsiaCh16Application.class, args);
    }

}
