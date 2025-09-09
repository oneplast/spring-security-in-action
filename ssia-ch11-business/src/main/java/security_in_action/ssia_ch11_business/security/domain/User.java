package security_in_action.ssia_ch11_business.security.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String code;
}
