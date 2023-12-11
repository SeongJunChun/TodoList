package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_semester.todolist.domain.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByDate(LocalDate date);
    List<Schedule> findByInterestsIn(Set<String> interests);
    List<Schedule> findByTagsIn(Set<String> tags);
}
