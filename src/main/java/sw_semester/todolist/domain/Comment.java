package sw_semester.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw_semester.todolist.comment.CommentCreateRequestDto;
import sw_semester.todolist.comment.CommentUpdateRequestDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Builder
    public Comment(String content, Article article, User user) {
        this.content = content;
        this.article = article;
        this.user = user;
    }

    public Comment(Article article, CommentCreateRequestDto commentCreateRequestDto, User user){
        this.content = commentCreateRequestDto.getContent();
        this.article = article;
        this.user = user;
    }

    public void update(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.content = commentUpdateRequestDto.getContent();
    }
}
