package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Article;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<Article> findAllByTagContaining(String tag);
}
