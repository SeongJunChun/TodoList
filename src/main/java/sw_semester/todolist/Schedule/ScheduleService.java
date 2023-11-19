package sw_semester.todolist.Schedule;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.repository.ScheduleRepository;

import java.awt.print.Book;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private InterestService interestService;

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Schedule findOne(Long id){
        return scheduleRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Schedule create(ScheduleRequestDto requestDto){
        Schedule schedule = new Schedule(requestDto);
        schedule.setTime(requestDto.getTime());

        List<String> validInterests = interestService.getAllInterests();

        List<String> interests = requestDto.getInterests().stream()
                .filter(validInterests::contains)
                .collect(Collectors.toList());

        schedule.setInterests(new HashSet<>(interests));


        if (requestDto.Repeat()) {
            schedule.setDaysOfWeek(requestDto.getDaysOfWeek());
            schedule.setRepeatEndDate(requestDto.getRepeatEndDate());
        }

        schedule.setTags(requestDto.getTags());

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void update(Long id, ScheduleRequestDto newScheduleDto){
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);
        if(schedule != null) {
            schedule.setHeadline(newScheduleDto.getHeadline());
            schedule.setContext(newScheduleDto.getContext());
            schedule.setDate(newScheduleDto.getDate());
            schedule.setUserId(newScheduleDto.getUserId());
            schedule.setTime(newScheduleDto.getTime());
        }

        if (newScheduleDto.getInterests() != null) {
            List<String> validInterests = interestService.getAllInterests();
            List<String> interests = newScheduleDto.getInterests().stream()
                    .filter(validInterests::contains)
                    .collect(Collectors.toList());
            schedule.setInterests(new HashSet<>(interests));
        }

        schedule.setIsRepeat(Optional.ofNullable(newScheduleDto.Repeat()).orElse(schedule.getIsRepeat()));

        schedule.setDaysOfWeek(Optional.ofNullable(newScheduleDto.getDaysOfWeek()).orElse(schedule.getDaysOfWeek()));

        schedule.setRepeatEndDate(Optional.ofNullable(newScheduleDto.getRepeatEndDate()).orElse(schedule.getRepeatEndDate()));

    }


    @Transactional
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);

        if (schedule != null) {
            scheduleRepository.delete(schedule);
        }
    }

    public void addInterest(String interest) {
        interestService.addInterest(interest);
    }

    public void removeInterest(String interest) {
        interestService.removeInterest(interest);
    }
}
