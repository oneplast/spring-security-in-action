package security_in_action.ssia_ch13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SsiaCh13Application {

	public static void main(String[] args) {
		SpringApplication.run(SsiaCh13Application.class, args);
	}

}
