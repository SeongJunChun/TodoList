package sw_semester.todolist.follow;
import sw_semester.todolist.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {
    private final FollowService followService;
    @PostMapping("/api/follow/{userId}")
    public Boolean followUser(@PathVariable Long userId, @AuthenticationPrincipal User user){
        if (user.getId().equals(userId)){
            throw new UserRequestException("본인을 팔로우할 수 없습니다.");
        }
        return followService.toggleUser(userId, user);
    }
}
