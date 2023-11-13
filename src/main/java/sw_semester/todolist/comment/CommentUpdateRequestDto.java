package sw_semester.todolist.comment;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;

    public CommentUpdateRequestDto(String content) {
        this.content = content;
    }
}
