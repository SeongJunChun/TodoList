package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sw_semester.todolist.domain.User;


@RequiredArgsConstructor
@RestController
public class MyInfoController {
    private final MyInfoService myInfoService;

    //유저 프로필 조회//
    @GetMapping("/api/myinfo")
    public MyInfoResponseDto readMyInfo(@AuthenticationPrincipal User user){


        return myInfoService.readMyInfo(user);
    }
    //유저 프로필 수정(닉네임, 프로필사진)//
    @PutMapping("/api/myinfo")
    public ProfileUpdateResponseDto updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto, Errors errors, @AuthenticationPrincipal User user){

        return myInfoService.updateProfile(profileUpdateRequestDto, user);
    }
}
