package sw_semester.todolist.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FollowResponseDto {
    private String myProfileImageUrl;
    private String memberName;
    private Long memberId;
    public FollowResponseDto(String myProfileImageUrl,String memberName,Long memberId){
        this.myProfileImageUrl=myProfileImageUrl;
        this.memberName=memberName;
        this.memberId=memberId;
    }
}
