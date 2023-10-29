package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow,Long> {
}
