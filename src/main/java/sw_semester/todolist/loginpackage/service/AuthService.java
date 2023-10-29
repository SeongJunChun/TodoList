package sw_semester.todolist.loginpackage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.UserInfo;
import sw_semester.todolist.loginpackage.controller.AuthenticationRequest;
import sw_semester.todolist.loginpackage.controller.AuthenticationResponse;
import sw_semester.todolist.loginpackage.controller.RegisterRequest;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.Role;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.loginpackage.token.Token;
import sw_semester.todolist.loginpackage.token.TokenRepository;
import sw_semester.todolist.loginpackage.token.TokenType;
import sw_semester.todolist.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository repository;
    private final UserInfoRepository userInfoRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwtservice jwtservice;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        UserInfo userInfo = UserInfo.builder().build();
        userInfoRepository.save(userInfo);

        var user = User.builder()
                .memberName(request.getName())
                .memberEmail(request.getEmail())
                .profileImageUrl("여기에 프로필 이미지URL 넣을거임")
                .memberPassword(passwordEncoder.encode(request.getPassword()))
                .userInfo(userInfo)
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
