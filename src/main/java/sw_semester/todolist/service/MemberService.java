package sw_semester.todolist.service;

import com.sun.source.tree.MemberReferenceTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sw_semester.todolist.controller.MemberController;
import sw_semester.todolist.dto.MemberDTO;
import sw_semester.todolist.entity.MemberEntity;
import sw_semester.todolist.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; // 먼저 jpa, mysql dependency 추가

    /* 회원가입 부분 멤버DTO를 받아 엔티티로 변환 리포지토리로 전달*/
    public void save(MemberDTO memberDTO) {
        // repsitory의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);//멤버 DTO받아 엔티티로 변환
        memberRepository.save(memberEntity);
        //Repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)

    }

    /**로그인부분 실패시 화면 전환 미구현**/
    public MemberDTO login(MemberDTO memberDTO){ //entity객체는 service에서만
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다
            MemberEntity memberEntity = byMemberEmail.get(); // Optional에서 꺼냄
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //비밀번호 일치
                //entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                //비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다
            return null;
        }
    }

}
