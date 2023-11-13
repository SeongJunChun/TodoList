package sw_semester.todolist.liked;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.Article;
import sw_semester.todolist.domain.Liked;
import sw_semester.todolist.repository.ArticleRepository;
import sw_semester.todolist.repository.LikedRepository;
import sw_semester.todolist.domain.User;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository likedRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Boolean toggleLiked(Long articleId, User user) {
        Optional<Article> article = articleRepository.findById(articleId);

        if (article.isPresent()) {
            for (Liked liked : article.get().getLikedList()) {
                if (liked.getUser().getId().equals(user.getId())) {
                    article.get().getLikedList().remove(liked);
                    liked.disconnectArticle();
                    likedRepository.delete(liked);
                    return false;
                }
            }
            likedRepository.save(Liked.builder()
                    .user(user)
                    .article(article.get())
                    .build());
            return true;
        } else {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
        }
    }
}
