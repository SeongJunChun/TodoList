package sw_semester.todolist.article;

import lombok.Getter;
import sw_semester.todolist.comment.CommentResponseDto;
import sw_semester.todolist.domain.Article;
import sw_semester.todolist.domain.Comment;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class ArticleResponseDto {

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String imageUrl;
    private final String content;
    private final Set<String> tag;
    private final String author;
    private final Long authorId;
    private final String authorProfileImageUrl;
    private final Long articleId;
    private Boolean isLiked;

    private final Long likeCount;

    private final List<CommentResponseDto> comments = new ArrayList<>();




    public ArticleResponseDto(Article article, boolean isLiked){
        this.createdAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();
        this.imageUrl = article.getImageUrl();
        this.content = article.getContent();
        this.tag = convertStringToTagSet(article.getTag());
        this.author = article.getUser().getMemberName();
        this.authorId=article.getUser().getId();
        this.authorProfileImageUrl = article.getUser().getProfileImageUrl();
        this.articleId = article.getId();
        this.likeCount = (long)article.getLikedList().size();
        this.isLiked = isLiked;
        for(Comment comment:article.getCommentList()){
            this.comments.add(new CommentResponseDto(comment));
        }
    }
    public static Set<String> convertStringToTagSet(String tagString) {
        Set<String> tagSet = new HashSet<>();

        if (tagString != null && !tagString.isEmpty()) {
            // Split the string by commas and trim whitespace
            String[] tagsArray = tagString.split(",");
            for (String tag : tagsArray) {
                tagSet.add(tag.trim());
            }
        }

        return tagSet;
    }
}
