package sw_semester.todolist.mypage;

import lombok.Getter;
import sw_semester.todolist.domain.User;

@Getter
public class ProfileUpdateResponseDto {
    private final String memberName;
    private final String profileImageUrl;
    private final String selfIntroduction;

    public ProfileUpdateResponseDto(String memberName, String profileImageUrl,String selfIntroduction){
        this.memberName = memberName;
        this.profileImageUrl = profileImageUrl;
        this.selfIntroduction = selfIntroduction;

    }
    public ProfileUpdateResponseDto(User user){
        this.memberName=user.getMemberName();
        this.profileImageUrl=user.getProfileImageUrl();
        this.selfIntroduction=user.getSelfIntroduction();
    }

}
