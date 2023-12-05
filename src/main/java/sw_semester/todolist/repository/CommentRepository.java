package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByArticle_Id(Long articleId);
}
