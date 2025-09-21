package security_in_action.ssia_ch18_resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class SsiaCh18ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsiaCh18ResourceApplication.class, args);
	}

}
