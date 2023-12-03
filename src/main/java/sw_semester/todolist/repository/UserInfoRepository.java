package sw_semester.todolist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.UserInfo;

import java.util.List;

public interface UserInfoRepository  extends JpaRepository<UserInfo,Long> {

}
