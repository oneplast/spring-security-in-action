package security_in_action.ssia_ch6.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import security_in_action.ssia_ch6.security.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String u);
}
