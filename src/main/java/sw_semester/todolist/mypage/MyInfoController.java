package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.util.S3Upload;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class MyInfoController {
    private final MyInfoService myInfoService;
    private final S3Upload s3Uploader;

    //유저 프로필 조회//
    @GetMapping("/api/myinfo")
    public MyInfoResponseDto readMyInfo(@AuthenticationPrincipal User user){


        return myInfoService.readMyInfo(user);
    }
    //유저 프로필 수정(닉네임, 프로필사진)//
    @PutMapping("/api/myinfo")
    public ProfileUpdateResponseDto updateProfile(@RequestPart("data") ProfileUpdateRequestDto profileUpdateRequestDto,
                                                  @AuthenticationPrincipal User user,
                                                  @RequestPart("image") MultipartFile multipartFile) throws IOException {

        return myInfoService.updateProfile(profileUpdateRequestDto, user,multipartFile);
    }
    @GetMapping("/api/myinfo/{searchKeyword}")
    public List<MyInfoResponseDto> searchMyInfo(@PathVariable(name="searchKeyword") String keyword){
        return myInfoService.searchMyInfo(keyword);
    }
    @GetMapping("/api/userinfo/{Id}")
    public MyInfoResponseDto getUser(@PathVariable(name="Id") Long UserId){
        return myInfoService.getUserInfo(UserId);
    }

}
