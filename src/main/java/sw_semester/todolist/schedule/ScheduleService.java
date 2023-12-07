package sw_semester.todolist.schedule;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private InterestService interestService;


    public List<Schedule> findByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

    @Transactional
    public ScheduleCreateResponseDto create(ScheduleRequestDto requestDto, User user){
        Schedule schedule = new Schedule(requestDto, user);
        schedule.setTime(requestDto.getTime());
        schedule.setIsDone(false);

        if (requestDto.Repeat()) {
            schedule.setDaysOfWeek(requestDto.getDaysOfWeek());
            schedule.setRepeatEndDate(requestDto.getRepeatEndDate());
        }

        List<String> validInterests = interestService.getAllInterests();

        List<String> interests = requestDto.getInterests().stream()
                .filter(validInterests::contains)
                .collect(Collectors.toList());

        schedule.setInterests(new HashSet<>(interests));
        schedule.setTags(requestDto.getTags());

        Schedule createdSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(
                createdSchedule.getId(),
                createdSchedule.getHeadline(),
                createdSchedule.getContext(),
                createdSchedule.getIsRepeat(),
                createdSchedule.getRepeatEndDate(),
                createdSchedule.getDate(),
                createdSchedule.getTime(),
                createdSchedule.getTags(),
                createdSchedule.getInterests(),
                createdSchedule.getUser(),
                createdSchedule.getDaysOfWeek()
        );
        //여기 리턴값을 ScheduleCreateResponse 이런식으로 DTO클래스 만들어서 위에 적용된 정보를 여기에 담아서 반환
        //DTO값은 반환값으로 보고싶은 정보를 설정하면됨
        //기능은 잘돌아가니 기능적인 부분은 안건들여도 될듯
        //그래도 안되면 붙잡고있다보면 언젠간됩니다
    }


    @Transactional
    public void update(Long id, ScheduleRequestDto newScheduleDto,User user){
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);
        if(schedule != null) {
            schedule.setHeadline(newScheduleDto.getHeadline());
            schedule.setContext(newScheduleDto.getContext());
            schedule.setDate(newScheduleDto.getDate());
            schedule.setTime(newScheduleDto.getTime());
        }

        if (newScheduleDto.getInterests() != null) {
            List<String> validInterests = interestService.getAllInterests();
            List<String> interests = newScheduleDto.getInterests().stream()
                    .filter(validInterests::contains)
                    .collect(Collectors.toList());
            schedule.setInterests(new HashSet<>(interests));
        }
        schedule.setTags(newScheduleDto.getTags());

        schedule.setIsRepeat(Optional.ofNullable(newScheduleDto.Repeat()).orElse(schedule.getIsRepeat()));
        if (newScheduleDto.Repeat()) {
            schedule.setDaysOfWeek(Optional.ofNullable(newScheduleDto.getDaysOfWeek()).orElse(schedule.getDaysOfWeek()));
            schedule.setRepeatEndDate(Optional.ofNullable(newScheduleDto.getRepeatEndDate()).orElse(schedule.getRepeatEndDate()));
        } else {
            schedule.setDaysOfWeek(null);
            schedule.setRepeatEndDate(null);
        }

        schedule.setIsDone(Optional.ofNullable(newScheduleDto.getIsDone()).orElse(schedule.getIsDone()));

        scheduleRepository.save(schedule);

    }


    @Transactional
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);

        if (schedule != null) {
            scheduleRepository.delete(schedule);
        }
    }

    @Transactional
    public List<Schedule> findByInterests(Set<String> interests) {
        return scheduleRepository.findByInterestsIn(interests);
    }

    @Transactional
    public List<Schedule> findByTags(String tags) {
        return scheduleRepository.findByTagsContaining(tags);

    }

}
