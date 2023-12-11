package sw_semester.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw_semester.todolist.domain.MonGoal;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonGoalRepository extends JpaRepository<MonGoal,Long> {
    List<MonGoal> findByMonthAndUserId(Long month, Long userId);
    Optional<MonGoal> findByIdAndUser(Long id, User user);
    List<MonGoal>findAllByYearAndUserId(Long year, Long userId);

}