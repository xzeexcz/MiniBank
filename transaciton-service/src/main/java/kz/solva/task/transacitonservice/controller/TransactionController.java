package kz.solva.task.transacitonservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @GetMapping("/get")
    public String getTrans() {
        return "hello,world!";
    }
}
