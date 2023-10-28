package sw_semester.todolist.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoResponseDto {
    private String myProfileImageUrl;
    private String memberName;
    private Long articleCount;
    private Long followerCount;
    private Long followCount;


    /*public MyInfoResponseDto(String myProfileImageUrl, String memberName, Long articleCount, Long followerCount, Long followCount) {
        this.myProfileImageUrl = myProfileImageUrl;
        this.memberName = memberName;
        this.articleCount = articleCount;
        this.followerCount = followerCount;
        this.followCount = followCount;
    }*/


}
