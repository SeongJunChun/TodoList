package sw_semester.todolist.liked;

import sw_semester.todolist.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikedController {

    private final LikedService likedService;
    @PostMapping("/api/articles/{articleId}/like")
    public Boolean likeArticle(@PathVariable Long articleId, @AuthenticationPrincipal User user){
        return likedService.toggleLiked(articleId,user);
    }
}
