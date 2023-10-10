package sw_semester.todolist.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.service.AuthService;


@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> resister(
            @RequestBody RegisterRequest request
    ){


        return ResponseEntity.ok(service.register(request));


    }
    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> resister(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }

}
