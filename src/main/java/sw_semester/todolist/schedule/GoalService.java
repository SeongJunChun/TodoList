package sw_semester.todolist.schedule;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_semester.todolist.domain.MonGoal;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;
import sw_semester.todolist.repository.MemberRepository;
import sw_semester.todolist.repository.MonGoalRepository;
import sw_semester.todolist.repository.YearGoalRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        return convertToYearGoalDto(yearGoal);
    }

    private YearGoalDto convertToYearGoalDto(YearGoal yearGoal) {
        YearGoalDto dto = new YearGoalDto();
        dto.setId(yearGoal.getId());
        dto.setYear(yearGoal.getYear());
        dto.setYearGoal(yearGoal.getYearGoal());
        dto.setIsDone(yearGoal.getIsDone());
        return dto;
    }

    @Transactional
    public MonGoalDto createMGoal(MonGoalDto monGoalDto, User user){
        MonGoal monGoal=new MonGoal(monGoalDto,user);
        monGoalRepository.save(monGoal);
        return convertToMonGoalDto(monGoal);
    }

    private MonGoalDto convertToMonGoalDto(MonGoal monGoal){
        MonGoalDto dto= new MonGoalDto();
        dto.setId(monGoal.getId());
        dto.setYear(monGoal.getYear());
        dto.setMonth(monGoal.getMonth());
        dto.setMonGoal(monGoal.getMonGoal());
        dto.setIsDone(monGoal.getIsDone());
        return dto;
    }

    @Transactional
    public YearGoalDto updateYGoal(Long id, String updatedYearGoal, Boolean isDone, User user) {
        YearGoal yearGoal = yearGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 연간 목표를 찾을 수 없습니다."));

        yearGoal.setYearGoal(updatedYearGoal);
        if (isDone != null) {
            yearGoal.setIsDone(isDone);
        }

        return new YearGoalDto(yearGoal.getId(), yearGoal.getYear(), yearGoal.getYearGoal(), yearGoal.getIsDone());
    }



    @Transactional
    public MonGoalDto updateMGoal(Long id, String updatedMonGoal, Boolean isDone, User user) {
        MonGoal monGoal = monGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("해당 월간 목표를 찾을 수 없습니다."));

        monGoal.setMonGoal(updatedMonGoal);
        if (isDone != null) {
            monGoal.setIsDone(isDone);
        }

        return new MonGoalDto(monGoal.getId(),monGoal.getYear(), monGoal.getMonth(), monGoal.getMonGoal(),monGoal.getIsDone());
    }

    @Transactional
    public List<YearGoal> findByYear(Long year,Long userId) {
        return yearGoalRepository.findByYearAndUserId(year,userId);
    }

    @Transactional
    public List<MonGoal> findByMonth(Long month, Long userId) {
        return monGoalRepository.findByMonthAndUserId(month,userId);
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

    @Transactional
    public List<AllGoalResponseDto> getYearAndMonthGoal(Long year, Long userId) {
        List<AllGoalResponseDto> allGoalResponseDtos = new ArrayList<>();

        List<YearGoal> yearGoals = yearGoalRepository.findByYearAndUserId(year, userId);

        for (YearGoal yearGoal : yearGoals) {
            List<MonGoal> monGoals = monGoalRepository.findAllByYearAndUserId(year, userId);
            for (MonGoal monGoal : monGoals) {
                AllGoalResponseDto allGoalResponseDto = new AllGoalResponseDto(
                        yearGoal.getYear(),
                        monGoal.getMonth(),
                        yearGoal.getYearGoal(),
                        monGoal.getMonGoal(),
                        yearGoal.getIsDone(),
                        monGoal.getIsDone()
                );
                allGoalResponseDtos.add(allGoalResponseDto);
            }
        }

        if (yearGoals.isEmpty()) {
            throw new EntityNotFoundException("해당 연간 목표를 찾을 수 없습니다.");
        }

        return allGoalResponseDtos;
    }
}
