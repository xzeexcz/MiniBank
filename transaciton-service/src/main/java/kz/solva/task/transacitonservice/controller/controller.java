package kz.solva.task.transacitonservice.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    @GetMapping("/get")
    public String hello() {
        return "Hello,world";
    }
}
