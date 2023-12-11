package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface YearGoalRepository extends JpaRepository<YearGoal,Long> {
    List<YearGoal> findByYearAndUserId(Long year,Long userId);
    Optional<YearGoal> findByIdAndUser(Long id, User user);

}
