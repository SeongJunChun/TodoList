package sw_semester.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sw_semester.todolist.controller.AuthenticationRequest;
import sw_semester.todolist.controller.AuthenticationResponse;
import sw_semester.todolist.controller.RegisterRequest;
import sw_semester.todolist.entity.User;
import sw_semester.todolist.entity.Role;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.token.Token;
import sw_semester.todolist.token.TokenRepository;
import sw_semester.todolist.token.TokenType;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwtservice jwtservice;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {


        var user = User.builder()
                .memberName(request.getName())
                .memberEmail(request.getEmail())
                .memberPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var saveUser = repository.save(user);
        var jwtToken = jwtservice.generateToken(user);
        saveUserToken(saveUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByMemberEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtservice.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    //유저 로그인/회원가입에 해당
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User saveUser, String jwtToken) {
        var token = Token.builder()
                .user(saveUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}
