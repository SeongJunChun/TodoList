package sw_semester.todolist.loginpackage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.UserInfo;
import sw_semester.todolist.loginpackage.controller.*;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.Role;
import sw_semester.todolist.loginpackage.exception.UserRequestException;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.loginpackage.token.Token;
import sw_semester.todolist.loginpackage.token.TokenRepository;
import sw_semester.todolist.loginpackage.token.TokenType;
import sw_semester.todolist.repository.UserInfoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        if(repository.findByMemberEmail(request.getEmail()).isPresent()){
            String errorMessage = "중복된 아이디가 존재합니다.";
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("email",errorMessage);
            throw new UserRequestException(errorMessage,errorMap);
        }
        if(repository.findByMemberName(request.getName()).isPresent()){
            String errorMessage = "중복된 이름이 존재합니다.";
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("name",errorMessage);
            throw new UserRequestException(errorMessage,errorMap);
        }
        UserInfo userInfo = UserInfo.builder().build();
        userInfoRepository.save(userInfo);

        var user = User.builder()
                .memberName(request.getName())
                .memberEmail(request.getEmail())
                .selfIntroduction("자기소개를 작성하세요")
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
        Optional<User> input = repository.findByMemberEmail(request.getEmail());
        if(input.isEmpty()){
            String errorMessage = "아이디가 존재하지 않습니다";
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("email",errorMessage);
            throw new UserRequestException(errorMessage,errorMap);
        }
        if(!passwordEncoder.matches(request.getPassword(),input.get().getMemberPassword())){
            String errorMessage = "잘못된 비밀번호 입니다.";
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("password",errorMessage);
            throw new UserRequestException(errorMessage,errorMap);
        }
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
                .userId(user.getId())
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

    public boolean checkPassword(PasswordRequest passwordRequest,User user){
        System.out.println();
        if(passwordEncoder.matches(passwordRequest.getPassword(),user.getMemberPassword())){
            return true;
        }
        return false;
    }
    public void changePassword(PasswordChangeRequest passwordChangeRequest,User user){
        if(!passwordChangeRequest.getChangePassword1().equals(passwordChangeRequest.getChangePassword2())){
            String errorMessage = "입력한 비밀번호가 일치하지 않습니다.";
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("password",errorMessage);
            throw new UserRequestException(errorMessage,errorMap);
        }
        user.setMemberPassword(passwordEncoder.encode(passwordChangeRequest.getChangePassword1()));
        repository.save(user);
    }
}
