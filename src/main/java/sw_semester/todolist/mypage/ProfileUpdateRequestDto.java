package sw_semester.todolist.mypage;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequestDto {
    @NotNull
    private String memberName;
    private String profileImageUrl;
    private String selfIntroduction;

    public ProfileUpdateRequestDto(String memberName, String profileImageUrl,String selfIntroduction){
        this.memberName = memberName;
        this.profileImageUrl = profileImageUrl;
        this.selfIntroduction = selfIntroduction;
    }
}
