package sw_semester.todolist.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.Follow;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.repository.FollowRepository;
import sw_semester.todolist.repository.MemberRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Boolean toggleUser(Long userId, User user){
        Optional<User> PreyUser = memberRepository.findById(userId);
        Optional<User> HunterUser = memberRepository.findById(user.getId());

        if (!HunterUser.isPresent()) {
            throw new UserRequestException("회원님의 계정을 찾을 수 없습니다.");
        }

        if(PreyUser.isPresent()){
            for(Follow follow :PreyUser.get().getFolloweeList()){
                if(follow.getFollower().getId().equals(HunterUser.get().getId())){
                    PreyUser.get().getFolloweeList().remove(follow); // 언팔.
                    HunterUser.get().getFollowerList().remove(follow);
                    follow.disconnectFollowee();
                    follow.disconnectFollower();

                    HunterUser.get().hasUnFollowing();
                    PreyUser.get().hasUnFollowed();

                    followRepository.delete(follow);
                    return false;
                }
            }
            followRepository.save(Follow.builder()
                    .follower(HunterUser.get())
                    .followee(PreyUser.get())
                    .build());
            HunterUser.get().hasFollowing();
            PreyUser.get().hasFollowed();
            return true;
        } else {
            throw new UserRequestException("해당 유저가 없습니다. userId=" + userId);
        }
    }
}
