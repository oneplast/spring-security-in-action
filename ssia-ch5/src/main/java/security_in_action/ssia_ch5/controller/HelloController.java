package security_in_action.ssia_ch5.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication a) {
        // MethodArgumentResolver 중 AuthenticationPrincipalArgumentResolver 작동
        // 여기서 객체 바인딩 할 때, SecurityContextHolder.getContext().getAuthentication() (+.getPrincipal()) 사용

        return "Hello, " + a.getName() + "!";
    }
}
