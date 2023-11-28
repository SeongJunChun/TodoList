package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.follow.FollowRequestException;
import sw_semester.todolist.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.Optional;

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
    @Transactional
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, User user) {
        Optional<User> found = memberRepository.findByMemberEmail(user.getMemberEmail());
        if (found.isPresent()) {
            found.get().update(profileUpdateRequestDto);
        } else {
            throw new FollowRequestException("해당 유저가 없습니다. id=" + user.getId());
        }
        return new ProfileUpdateResponseDto(found.get());
    }


}
