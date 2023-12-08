package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sw_semester.todolist.domain.Follow;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByFolloweeId(Long id);
    List<Follow> findAllByFollowerId(Long id);
}
