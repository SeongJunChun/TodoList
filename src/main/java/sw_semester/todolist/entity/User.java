package sw_semester.todolist.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sw_semester.todolist.loginpackage.token.Token;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
@Table(name = "user_table")
/*엔티티 선언부분 여기서 오류발생시 컬럼명을 확인해야함 memberEmail==member_email 등 테이블로 넘겨줄때 jpa표준 문법 확인하기*/
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long  id;

    @Column(unique = true)
    private String memberEmail;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = true)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private UserInfo userInfo;



    public void hasWroteArticle(){
        this.userInfo.articleCountPlus();
    }
    public void hasDeletedArticle(){
        this.userInfo.articleCountMinus();
    }

    public void hasFollowed(){
        this.userInfo.followerCountPlus();
    }
    public void hasUnFollowed(){
        this.userInfo.followerCountMinus();
    }

    public void hasFollowing(){
        this.userInfo.followCountPlus();
    }
    public void hasUnFollowing(){
        this.userInfo.followCountMinus();
    }

    /*DTO로 받아온값 엔티티로 변확해서 저장하는 부분 타 클래스에서 호출해서 사용*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
