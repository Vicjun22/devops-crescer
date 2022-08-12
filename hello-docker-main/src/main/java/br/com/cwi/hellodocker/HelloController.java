package br.com.cwi.hellodocker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {    
    @GetMapping("/")
    public String index() {
        return "Index!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }    
}
