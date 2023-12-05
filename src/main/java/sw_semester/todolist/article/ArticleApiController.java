package sw_semester.todolist.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.repository.ArticleRepository;
import sw_semester.todolist.domain.User;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ArticleResponseDto createArticle(@RequestBody ArticleCreateRequestDto articleCreateRequestDto,  @AuthenticationPrincipal User user){

        return articleService.createArticle(articleCreateRequestDto,user);
    }

    @GetMapping("/api/articles")
    public List<ArticleResponseDto> readArticles(@AuthenticationPrincipal User user){

        return articleService.readArticles(user);
    }

    @GetMapping("/api/articles/search/{keyword}")
    public List<ArticleResponseDto> searchArticles(@PathVariable(name="keyword") String keyword, @AuthenticationPrincipal User user){

        return articleService.searchArticles(keyword,user);
    }

    @GetMapping("/api/articles/{articleId}")
    public ArticleResponseDto readArticle(@PathVariable Long articleId, @AuthenticationPrincipal User user){
        return articleService.readArticle(articleId, user);
    }


    //사진 수정 안됨 내수정만
    @PutMapping("/api/articles/{articleId}")
    public ArticleResponseDto updateArticle(@PathVariable Long articleId, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto , @AuthenticationPrincipal User user){
        return articleService.updateArticle(articleId, articleUpdateRequestDto,user);
    }


    @DeleteMapping("/api/articles/{articleId}")
    public void deleteArticle(@PathVariable Long articleId , @AuthenticationPrincipal User user){
        articleService.deleteArticle(articleId,user);
    }
}
