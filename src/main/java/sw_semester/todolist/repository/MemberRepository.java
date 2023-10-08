package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_semester.todolist.entity.MemberEntity;

import java.util.Optional;
/*말그대로 저장소임 DTO받아온 값을 여기에 CURD한다 생각*/
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
