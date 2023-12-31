package sw_semester.todolist.comment;

import lombok.Getter;
import sw_semester.todolist.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String commentAuthor;

    private final Long commentAuthorId;
    private final String commentAuthorProfileImageUrl;
    private final Long articleId;
    private final Long commentId;
    private final String content;

//    @Builder
//    public CommentResponseDto(LocalDateTime createdAt, LocalDateTime modifiedAt, String commentAuthor, String commentAuthorProfileImageUrl) {
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//        this.commentAuthor = commentAuthor;
//        this.commentAuthorProfileImageUrl = commentAuthorProfileImageUrl;
//    }


    public CommentResponseDto(Comment comment){
        this.articleId = comment.getArticle().getId();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.commentAuthor = comment.getUser().getMemberName();
        this.commentAuthorProfileImageUrl = comment.getUser().getProfileImageUrl();
        this.commentAuthorId = comment.getUser().getId();
    }
    public CommentResponseDto(Long articleId, Long commentId, String content,
                              LocalDateTime createdAt, LocalDateTime modifiedAt,
                               String commentAuthor, String commentAuthorProfileImageUrl,Long userId){
        this.articleId = articleId;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentAuthor = commentAuthor;
        this.commentAuthorProfileImageUrl = commentAuthorProfileImageUrl;
        this.commentAuthorId = userId;
    }
}
