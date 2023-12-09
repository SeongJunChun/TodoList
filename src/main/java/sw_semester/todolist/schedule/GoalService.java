package sw_semester.todolist.schedule;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_semester.todolist.domain.MonGoal;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.repository.MonGoalRepository;
import sw_semester.todolist.repository.YearGoalRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class GoalService {
    @Autowired
    private final MonGoalRepository monGoalRepository;
    @Autowired
    private final YearGoalRepository yearGoalRepository;
    @Autowired
    private final MemberRepository memberRepository;


    @Transactional
    public YearGoalDto createYGoal(YearGoalDto yearGoalDto, User user){
        YearGoal yearGoal=new YearGoal(yearGoalDto,user);
        yearGoalRepository.save(yearGoal);
        return yearGoalDto;
    }
    @Transactional
    public MonGoalDto createMGoal(MonGoalDto monGoalDto, User user){
        MonGoal monGoal=new MonGoal(monGoalDto,user);
        monGoalRepository.save(monGoal);
        return monGoalDto;
    }

    @Transactional
    public YearGoalDto updateYGoal(Long id, String updatedYearGoal, User user) {
        YearGoal yearGoal = yearGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 연간 목표를 찾을 수 없습니다."));

        yearGoal.setYearGoal(updatedYearGoal);

        return new YearGoalDto(yearGoal.getId(),yearGoal.getYear(), yearGoal.getYearGoal());
    }

    @Transactional
    public MonGoalDto updateMGoal(Long id, String updatedMonGoal, User user) {
        MonGoal monGoal = monGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 월간 목표를 찾을 수 없습니다."));

        monGoal.setMonGoal(updatedMonGoal);

        return new MonGoalDto(monGoal.getId(),monGoal.getYear(), monGoal.getMonth(), monGoal.getMonGoal());
    }


    @Transactional
    public List<YearGoal> findByYear(Long year,User user) {
        return yearGoalRepository.findByYear(year);
    }

    @Transactional
    public List<MonGoal> findByMonth(Long month, User user) {
        return monGoalRepository.findByMonth(month);
    }

    @Transactional
    public void deleteYearGoal(Long id) {
        YearGoal yearGoal = yearGoalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 연간 목표를 찾을 수 없습니다."));

        yearGoalRepository.delete(yearGoal);
    }

    @Transactional
    public void deleteMonGoal(Long id) {
        MonGoal monGoal = monGoalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 월간 목표를 찾을 수 없습니다."));

        monGoalRepository.delete(monGoal);
    }

}
