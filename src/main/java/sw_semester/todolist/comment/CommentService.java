package sw_semester.todolist.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.Article;
import sw_semester.todolist.domain.Comment;
import sw_semester.todolist.repository.ArticleRepository;
import sw_semester.todolist.repository.CommentRepository;
import sw_semester.todolist.domain.User;

import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<CommentResponseDto> getAllComments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticle_Id(articleId);

        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getArticle().getId(),
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt(),
                    comment.getUser().getMemberName(),
                    comment.getUser().getProfileImageUrl()
            );
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }

    public void createComment(Long articleId, CommentCreateRequestDto commentCreateRequestDto, User user) {

        Optional<Article> article = articleRepository.findById(articleId);

        if (article.isPresent()) {
            Comment comment = new Comment(article.get(), commentCreateRequestDto, user);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);

        }
    }

    @Transactional
    public CommentResponseDto updateComment(Long articleId, Long commentId, CommentUpdateRequestDto commentUpdateRequestDto, User user) {

        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            if (comment.get().getUser().getId().equals(user.getId())) {
                comment.get().update(commentUpdateRequestDto);
            } else {
                throw new IllegalArgumentException("로그인 한 사용자와 댓글 작성자가 다릅니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId);
        }

        return new CommentResponseDto(comment.get());
    }

    public void deleteComment(Long articleId, Long commentId, User user) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            if (comment.get().getUser().getId().equals(user.getId())) {
                commentRepository.delete(comment.get());
            } else {
                throw new IllegalArgumentException("로그인 한 사용자와 댓글 작성자가 다릅니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId);
        }
    }
}
