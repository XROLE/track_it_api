package trackit.trackit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Welcome to Track It endpoint. To view documentation visit https://wwww.trackit.dev";
    }
    
}