package sw_semester.todolist.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.repository.CommentRepository;
import sw_semester.todolist.domain.User;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @PostMapping("/api/articles/{articleId}/comments")
    public List<CommentResponseDto> createComment(@PathVariable Long articleId,  @RequestBody CommentCreateRequestDto commentCreateRequestDto,  @AuthenticationPrincipal User user){

        commentService.createComment(articleId, commentCreateRequestDto, user);
        return commentService.getAllComments(articleId);
    }


    @PutMapping("/api/articles/{articleId}/comments/{commentId}")
    public List<CommentResponseDto> updateComment(@PathVariable Long articleId, @PathVariable Long commentId,  @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,  @AuthenticationPrincipal User user){
        commentService.updateComment(articleId, commentId, commentUpdateRequestDto, user);
        return commentService.getAllComments(articleId);
    }

    @DeleteMapping("/api/articles/{articleId}/comments/{commentId}")
    public List<CommentResponseDto> deleteComment(@PathVariable Long articleId, @PathVariable Long commentId, @AuthenticationPrincipal User user){
        commentService.deleteComment(articleId, commentId, user);
        return commentService.getAllComments(articleId);
    }
}
