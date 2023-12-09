package sw_semester.todolist.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sw_semester.todolist.repository.ArticleRepository;
import sw_semester.todolist.domain.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ArticleResponseDto createArticle(@RequestPart("data") ArticleCreateRequestDto articleCreateRequestDto,
                                            @AuthenticationPrincipal User user,
                                            @RequestPart("image") MultipartFile multipartFile) throws IOException {

        return articleService.createArticle(articleCreateRequestDto,user,multipartFile);
    }

    @GetMapping("/api/articles")
    public List<ArticleResponseDto> readAllArticles(@AuthenticationPrincipal User user){

        return articleService.readAllArticles(user);
    }
    @GetMapping("/api/userArticles/{userId}")
    public List<ArticleResponseDto> readArticles(@AuthenticationPrincipal User user,@PathVariable(name="userId") Long userId){

        return articleService.readArticles(user,userId);
    }

    @GetMapping("/api/articles/search")
    public List<ArticleResponseDto> searchArticles(@RequestParam(name="keyword") Set<String> keyword, @RequestParam(name="method") String method, @AuthenticationPrincipal User user) {
        return articleService.searchArticles(keyword,method,user);
    }

    @GetMapping("/api/articles/{articleId}")
    public ArticleResponseDto readArticle(@PathVariable Long articleId, @AuthenticationPrincipal User user){
        return articleService.readArticle(articleId, user);
    }


    //사진 수정 안됨 내수정만
    @PutMapping("/api/articles/{articleId}")
    public boolean updateArticle(@PathVariable Long articleId, @RequestBody ArticleUpdateRequestDto articleUpdateRequestDto , @AuthenticationPrincipal User user){
        return articleService.updateArticle(articleId, articleUpdateRequestDto,user);
    }


    @DeleteMapping("/api/articles/{articleId}")
    public boolean deleteArticle(@PathVariable Long articleId , @AuthenticationPrincipal User user){
        return articleService.deleteArticle(articleId,user);
    }
}
