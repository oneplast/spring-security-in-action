package security_in_action.ssia_ch11.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import security_in_action.ssia_ch11.security.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}
