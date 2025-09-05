package security_in_action.ssia_ch6.security.service;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security_in_action.ssia_ch6.security.model.CustomUserDetails;
import security_in_action.ssia_ch6.security.model.entity.User;
import security_in_action.ssia_ch6.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication!");

        User u = userRepository.findUserByUsername(username).orElseThrow(s);

        return new CustomUserDetails(u);
    }
}
