package sw_semester.todolist.article;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleCreateRequestDto {
    private String content;

    @NotNull
    private String tag;


    public ArticleCreateRequestDto(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }
}
