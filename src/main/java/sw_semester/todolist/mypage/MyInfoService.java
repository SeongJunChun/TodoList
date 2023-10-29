package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MyInfoService {
    private final MemberRepository memberRepository;
    public MyInfoResponseDto readMyInfo(User user){
        User contextUser = memberRepository.findByMemberEmail(user.getMemberEmail()).get();



        return MyInfoResponseDto.builder()
                .myProfileImageUrl(contextUser.getProfileImageUrl())
                .memberName(contextUser.getMemberName())
                .selfIntroduction(contextUser.getSelfIntroduction())
                .articleCount(contextUser.getUserInfo().getArticleCount())
                .followerCount(contextUser.getUserInfo().getFollowerCount())
                .followCount(contextUser.getUserInfo().getFollowCount())
                .build();
    }

}
