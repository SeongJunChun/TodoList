package sw_semester.todolist.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sw_semester.todolist.domain.Follow;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.follow.FollowRequestException;
import sw_semester.todolist.loginpackage.exception.UserRequestException;
import sw_semester.todolist.repository.FollowRepository;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.repository.UserInfoRepository;
import sw_semester.todolist.util.S3Upload;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MyInfoService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final S3Upload s3Uploader;
    public MyInfoResponseDto readMyInfo(User user){
        User contextUser = memberRepository.findByMemberEmail(user.getMemberEmail()).get();

        return MyInfoResponseDto.builder()
                .myProfileImageUrl(contextUser.getProfileImageUrl())
                .memberName(contextUser.getMemberName())
                .selfIntroduction(contextUser.getSelfIntroduction())
                .articleCount(contextUser.getUserInfo().getArticleCount())
                .followerCount(contextUser.getUserInfo().getFollowerCount())
                .followCount(contextUser.getUserInfo().getFollowCount())
                .memberId(contextUser.getId())
                .build();
    }
    @Transactional
    public ProfileUpdateResponseDto updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, User user, MultipartFile image) throws IOException {
        Optional<User> found = memberRepository.findByMemberEmail(user.getMemberEmail());
        if (found.isPresent()) {
            if(memberRepository.findByMemberName(profileUpdateRequestDto.getMemberName()).isPresent()){
                String errorMessage = "중복된 이름이 존재합니다.";
                Map<String,String> errorMap = new HashMap<>();
                errorMap.put("name",errorMessage);
                throw new UserRequestException(errorMessage,errorMap);
            }
            String profileImg =found.get().getProfileImageUrl();
            if (!profileImg.equals("https://todoserver.s3.ap-northeast-2.amazonaws.com/static/%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84.jpg")){
                s3Uploader.fileDelete(profileImg);
            }
            String postImg = s3Uploader.upload(image, "images");
            found.get().update(profileUpdateRequestDto,postImg);
        } else {
            throw new FollowRequestException("해당 유저가 없습니다. id=" + user.getId());
        }
        return new ProfileUpdateResponseDto(found.get());
    }
    public List<MyInfoResponseDto> searchMyInfo(String keyword, User me){
        List<User> users = memberRepository.findAllByMemberNameContaining(keyword);
        List<MyInfoResponseDto> infoList = new ArrayList<>();
        for (User user : users){
            infoList.add(getUserInfo(user.getId(), me));
        }
        return infoList;
    }
    public MyInfoResponseDto getUserInfo(Long userId,User user){
        User contextUser  = memberRepository.findById(userId).get();
        boolean isFollow = contextUser.getFolloweeList().stream()
                .anyMatch(follow -> follow.getFollower().getId().equals(user.getId()));

        return MyInfoResponseDto.builder()
                .myProfileImageUrl(contextUser.getProfileImageUrl())
                .memberName(contextUser.getMemberName())
                .selfIntroduction(contextUser.getSelfIntroduction())
                .articleCount(contextUser.getUserInfo().getArticleCount())
                .followerCount(contextUser.getUserInfo().getFollowerCount())
                .followCount(contextUser.getUserInfo().getFollowCount())
                .memberId(contextUser.getId())
                .isFollowUser(isFollow)
                .build();
    }



}
