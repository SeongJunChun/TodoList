package sw_semester.todolist.mypage;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequestDto {
    @NotNull
    private String memberName;
    private String selfIntroduction;

    public ProfileUpdateRequestDto(String memberName,String selfIntroduction){
        this.memberName = memberName;
        this.selfIntroduction = selfIntroduction;
    }
}
