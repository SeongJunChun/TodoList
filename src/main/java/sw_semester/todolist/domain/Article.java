package sw_semester.todolist.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import sw_semester.todolist.article.ArticleCreateRequestDto;
import sw_semester.todolist.article.ArticleUpdateRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Article extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;


    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String tag;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private final List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    public void update(ArticleUpdateRequestDto articleUpdateRequestDto){
        this.content = articleUpdateRequestDto.getContent();
    }

    public Article(ArticleCreateRequestDto articleCreateRequestDto, User user){
        this.content = articleCreateRequestDto.getContent();
        this.imageUrl = articleCreateRequestDto.getImageUrl();
        this.user = user;
        this.tag = articleCreateRequestDto.getTag();
    }


}
