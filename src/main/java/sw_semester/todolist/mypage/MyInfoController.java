package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sw_semester.todolist.entity.User;

@RequiredArgsConstructor
@RestController
public class MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping("/api/myinfo")
    public MyInfoResponseDto readMyInfo(@AuthenticationPrincipal User user){


        return myInfoService.readMyInfo(user);
    }
}
