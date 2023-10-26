package sw_semester.todolist.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.service.AuthService;


@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService service;
    @PostMapping("/join")
    public ResponseEntity<AuthenticationResponse> resister(
            @RequestBody RegisterRequest request
    ){


        return ResponseEntity.ok(service.register(request));


    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
