package security_in_action.ssia_ch10.security.csrf.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import security_in_action.ssia_ch10.security.csrf.entity.Token;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findTokenByIdentifier(String identifier);
}
