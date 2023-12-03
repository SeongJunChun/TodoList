package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findAllByContentContainingOrTagContaining(String content,String tag);
}
