package sw_semester.todolist.follow;
import org.springframework.web.bind.annotation.GetMapping;
import sw_semester.todolist.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FollowController {
    private final FollowService followService;
    @PostMapping("/api/follow/{userId}")
    public Boolean followUser(@PathVariable Long userId, @AuthenticationPrincipal User user){
        if (user.getId().equals(userId)){
            throw new FollowRequestException("본인을 팔로우할 수 없습니다.");
        }
        return followService.toggleUser(userId, user);
    }
    @GetMapping("/api/follower/{userId}")
    public List<FollowResponseDto> getFollower(@PathVariable(name="userId") Long user){
        return followService.getFollowers(user);
    }
    @GetMapping("/api/followee/{userId}")
    public List<FollowResponseDto> getFollowee(@PathVariable(name="userId") Long user){
        return followService.getFollowees(user);
    }
}
