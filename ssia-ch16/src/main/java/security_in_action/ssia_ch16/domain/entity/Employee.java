package security_in_action.ssia_ch16.domain.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Employee {

    private String name;
    private List<String> books;
    private List<String> roles;
}
