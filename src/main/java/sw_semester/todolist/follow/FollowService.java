package sw_semester.todolist.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.domain.Follow;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.repository.FollowRepository;
import sw_semester.todolist.repository.MemberRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
            throw new FollowRequestException("회원님의 계정을 찾을 수 없습니다.");
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
            throw new FollowRequestException("해당 유저가 없습니다. userId=" + userId);
        }
    }
    public List<FollowResponseDto> getFollowers(User user){
        List<FollowResponseDto> followResponseDtos = new ArrayList<>();
        List<Follow> followers = followRepository.findAllByFollowerId(user.getId());
        for(Follow user1 : followers){
            User user1Follower = user1.getFollowee();
            FollowResponseDto dto = new FollowResponseDto(user1Follower.getProfileImageUrl(), user1Follower.getMemberName(), user1Follower.getId());
            followResponseDtos.add(dto);
        }
        return followResponseDtos;
    }
    public List<FollowResponseDto> getFollowees(User user){
        List<FollowResponseDto> followResponseDtos = new ArrayList<>();
        List<Follow> followers = followRepository.findAllByFolloweeId(user.getId());
        for(Follow user1 : followers){
            User user1Follower = user1.getFollower();
            FollowResponseDto dto = new FollowResponseDto(user1Follower.getProfileImageUrl(), user1Follower.getMemberName(), user1Follower.getId());
            followResponseDtos.add(dto);
        }
        return followResponseDtos;
    }
}
