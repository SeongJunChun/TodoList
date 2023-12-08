package sw_semester.todolist.article;

import lombok.RequiredArgsConstructor;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sw_semester.todolist.domain.Article;
import sw_semester.todolist.domain.Liked;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.repository.ArticleRepository;
import sw_semester.todolist.repository.LikedRepository;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.util.S3Upload;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository userRepository;
    private final LikedRepository likedRepository;
    private final S3Upload s3Uploader;

    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto articleCreateRequestDto, User user, MultipartFile image) throws IOException {
        String postImg = s3Uploader.upload(image, "images");
        Article article = new Article(articleCreateRequestDto, user,postImg);
        articleRepository.save(article);
        //contextUser = 실제 해당 User를 영속성 컨텍스트에 올림.
        Optional<User> contextUser = userRepository.findById(user.getId());
        contextUser.get().hasWroteArticle();
        return new ArticleResponseDto(article, false);
    }

    public List<ArticleResponseDto> readAllArticles(User user) {
        List<Article> articles = articleRepository.findAllByOrderByCreatedAtDesc();

        if (user == null) { // 로그인 하지 않은 사용자
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            for (Article article : articles) {
                articleResponseDtoList.add(new ArticleResponseDto(article, false));
            }

            return articleResponseDtoList;
        } else {
            Optional<User> contextUser = userRepository.findById(user.getId());
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            if (!contextUser.isPresent()) {
                throw new IllegalArgumentException("뭔가...문제가 있음...");
            }

            boolean isLike;
            for (Article article : articles) {
                isLike = false;
                for (Liked liked : contextUser.get().getLikedList()) {
                    if (liked.getArticle().getId().equals(article.getId())) {
                        isLike = true;
                        break;
                    }
                }
                articleResponseDtoList.add(new ArticleResponseDto(article, isLike));
            }

            return articleResponseDtoList;
        }
    }
    public List<ArticleResponseDto> readArticles(User user,Long userId) {
        List<Article> articles = articleRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        if (user == null) { // 로그인 하지 않은 사용자
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            for (Article article : articles) {
                articleResponseDtoList.add(new ArticleResponseDto(article, false));
            }

            return articleResponseDtoList;
        } else {
            Optional<User> contextUser = userRepository.findById(user.getId());
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            if (!contextUser.isPresent()) {
                throw new IllegalArgumentException("뭔가...문제가 있음...");
            }

            boolean isLike;
            for (Article article : articles) {
                isLike = false;
                for (Liked liked : contextUser.get().getLikedList()) {
                    if (liked.getArticle().getId().equals(article.getId())) {
                        isLike = true;
                        break;
                    }
                }
                articleResponseDtoList.add(new ArticleResponseDto(article, isLike));
            }

            return articleResponseDtoList;
        }
    }


        public ArticleResponseDto readArticle(Long articleId, User user) {
            Optional<Article> article = articleRepository.findById(articleId);

            if (user == null) {
                if (article.isPresent()) {
                    return new ArticleResponseDto(article.get(), false);
                } else {
                    throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
                }
            } else {
                Optional<User> contextUser = userRepository.findById(user.getId());

                if (!contextUser.isPresent()) {
                    throw new IllegalArgumentException("뭔가...문제가 있음...");
                }

                if (article.isPresent()) {

                    boolean isLike = false;

                    for (Liked liked : contextUser.get().getLikedList()) {
                        if (liked.getArticle().getId().equals(article.get().getId())) {
                            isLike = true;
                            break;
                        }
                    }
                    return new ArticleResponseDto(article.get(), isLike);

                } else {
                    throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
                }
            }
        }
    public List<ArticleResponseDto> searchArticles(String keyword,User user) {
        List<Article> articles = articleRepository.findAllByContentContainingOrTagContaining(keyword,keyword);

        if (user == null) { // 로그인 하지 않은 사용자
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            for (Article article : articles) {
                articleResponseDtoList.add(new ArticleResponseDto(article, false));
            }

            return articleResponseDtoList;
        } else {
            Optional<User> contextUser = userRepository.findById(user.getId());
            List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

            if (!contextUser.isPresent()) {
                throw new IllegalArgumentException("뭔가...문제가 있음...");
            }

            boolean isLike;
            for (Article article : articles) {
                isLike = false;
                for (Liked liked : contextUser.get().getLikedList()) {
                    if (liked.getArticle().getId().equals(article.getId())) {
                        isLike = true;
                        break;
                    }
                }
                articleResponseDtoList.add(new ArticleResponseDto(article, isLike));
            }

            return articleResponseDtoList;
        }
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleUpdateRequestDto articleUpdateRequestDto, User user) {
        Optional<Article> article = articleRepository.findById(articleId);
        Optional<User> contextUser = userRepository.findById(user.getId());

        if (article.isPresent()) {
            if (article.get().getUser().getId().equals(user.getId())) {
                article.get().update(articleUpdateRequestDto);
                boolean isLiked = false;
                for(Liked liked:contextUser.get().getLikedList()){
                    if(liked.getArticle().getId().equals(article.get().getId())){
                        isLiked = true;
                        break;
                    }
                }
                return new ArticleResponseDto(article.get(), isLiked);
            } else {
                throw new IllegalArgumentException("로그인 한 사용자와 게시물 작성자가 다릅니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
        }


    }

    @Transactional
    public void deleteArticle(Long articleId, User user) {
        Optional<Article> article = articleRepository.findById(articleId);
        Optional<User> contextUser = userRepository.findById(user.getId());
        if (article.isPresent()) {
            if (article.get().getUser().getId().equals(user.getId())) {
                s3Uploader.fileDelete(article.get().getImageUrl());
                articleRepository.delete(article.get());

                contextUser.get().hasDeletedArticle();
            } else {
                throw new IllegalArgumentException("로그인 한 사용자와 게시물 작성자가 다릅니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 게시글이 없습니다. id=" + articleId);
        }
    }

}
