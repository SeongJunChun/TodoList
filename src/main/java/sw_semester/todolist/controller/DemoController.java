package sw_semester.todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/demo")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        System.out.println("ffffffffffffff");
        return ResponseEntity.ok("Hello from secured endpoint");
    }

}
