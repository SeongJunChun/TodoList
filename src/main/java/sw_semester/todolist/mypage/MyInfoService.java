package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.entity.User;
import sw_semester.todolist.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MyInfoService {
    private final MemberRepository memberRepository;
    public MyInfoResponseDto readMyInfo(User user){
        User contextUser = memberRepository.findByMemberEmail(user.getMemberEmail()).get();
        System.out.println("-----------------"+contextUser.getMemberName()+"_______________________");



        return MyInfoResponseDto.builder()
                .myProfileImageUrl(contextUser.getProfileImageUrl())
                .memberName(contextUser.getMemberName())
                .articleCount(contextUser.getUserInfo().getArticleCount())
                .followerCount(contextUser.getUserInfo().getFollowerCount())
                .followCount(contextUser.getUserInfo().getFollowCount())
                .build();
    }

}
