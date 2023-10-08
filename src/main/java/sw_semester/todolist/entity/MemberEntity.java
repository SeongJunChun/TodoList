package sw_semester.todolist.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import sw_semester.todolist.dto.MemberDTO;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "user_table")
/*엔티티 선언부분 여기서 오류발생시 컬럼명을 확인해야함 memberEmail==member_email 등 테이블로 넘겨줄때 jpa표준 문법 확인하기*/
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    /*DTO로 받아온값 엔티티로 변확해서 저장하는 부분 타 클래스에서 호출해서 사용*/
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

}
