package sw_semester.todolist.comment;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private String content;

    public CommentCreateRequestDto(String content) {
        this.content = content;
    }
}
