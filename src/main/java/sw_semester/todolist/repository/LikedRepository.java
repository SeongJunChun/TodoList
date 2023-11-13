package sw_semester.todolist.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Article;
import sw_semester.todolist.domain.Liked;

import java.util.List;
import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByArticleAndUser(Article article, User user);
    List<Liked> findAllByArticle(Article article);
}
