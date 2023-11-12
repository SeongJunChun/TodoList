package sw_semester.todolist.article;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUpdateRequestDto {
    private String content;

    public ArticleUpdateRequestDto(String content) {
        this.content = content;
    }
}
