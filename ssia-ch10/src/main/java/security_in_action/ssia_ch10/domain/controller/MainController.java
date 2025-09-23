package security_in_action.ssia_ch10.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/test")
    @ResponseBody
//    @CrossOrigin("http://localhost:8080")
    public String test() {
        log.info("Test method called");
        return "HELLO";
    }
}
