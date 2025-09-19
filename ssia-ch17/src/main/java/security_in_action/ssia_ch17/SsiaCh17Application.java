package security_in_action.ssia_ch17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class SsiaCh17Application {

    public static void main(String[] args) {
        SpringApplication.run(SsiaCh17Application.class, args);
    }

}
