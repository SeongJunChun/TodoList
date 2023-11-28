package sw_semester.todolist.loginpackage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.follow.FollowRequestException;
import sw_semester.todolist.loginpackage.exception.UserRequestException;
import sw_semester.todolist.loginpackage.service.AuthService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService service;
    @PostMapping("/join")
    public ResponseEntity<AuthenticationResponse> resister(
            @Valid @RequestBody RegisterRequest request, Errors errors
    ){
        if (errors.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : errors.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                throw new UserRequestException("유효성 검사 실패함",errorMap);
            }
        }
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
