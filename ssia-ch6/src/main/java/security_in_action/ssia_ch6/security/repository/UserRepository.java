package security_in_action.ssia_ch6.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import security_in_action.ssia_ch6.security.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u JOIN FETCH u.authorities where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);
}
