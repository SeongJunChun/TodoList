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
    private String selfIntroduction;
    private Long articleCount;
    private Long followerCount;
    private Long followCount;




}
