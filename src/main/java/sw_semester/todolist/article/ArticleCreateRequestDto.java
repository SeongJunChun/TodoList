package sw_semester.todolist.article;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class ArticleCreateRequestDto {
    private String content;

    @NotNull
    private Set<String> tag;


    public ArticleCreateRequestDto(String content, Set<String> tag) {
        this.content = content;
        this.tag = tag;
    }
}
