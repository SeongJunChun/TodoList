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

    @NotNull
    private String imageUrl;

    public ArticleCreateRequestDto(String content, String imageUrl,String tag) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.tag = tag;
    }
}
