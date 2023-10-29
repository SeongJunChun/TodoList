package sw_semester.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.UserInfo;

public interface UserInfoRepository  extends JpaRepository<UserInfo,Long> {
}
