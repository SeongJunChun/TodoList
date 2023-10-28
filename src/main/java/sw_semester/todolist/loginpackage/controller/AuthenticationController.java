package sw_semester.todolist.loginpackage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.loginpackage.service.AuthService;


@RestController
@RequestMapping("/api/")
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
