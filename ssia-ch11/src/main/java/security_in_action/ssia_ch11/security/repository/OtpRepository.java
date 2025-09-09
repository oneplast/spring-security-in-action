package security_in_action.ssia_ch11.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import security_in_action.ssia_ch11.security.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findOtpByUsername(String username);
}
