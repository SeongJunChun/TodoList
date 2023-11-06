package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_semester.todolist.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
