package security_in_action.ssia_ch20.controller;

import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication a) {
        String message = StringUtils.hasText(a.getName()) && !a.getName().equals("user") ? ", " + a.getName() : "";
        return "Hello" + message + "!";
    }
}
