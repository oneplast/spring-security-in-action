package security_in_action.ssia_ch16.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import security_in_action.ssia_ch16.domain.service.NameService;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final NameService nameService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, " + nameService.getName();
    }
}
