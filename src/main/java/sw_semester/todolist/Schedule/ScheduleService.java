package sw_semester.todolist.Schedule;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.repository.ScheduleRepository;

import java.awt.print.Book;
import java.util.List;

@AllArgsConstructor
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

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
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule update(Long id, ScheduleRequestDto newScheduleDto){
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);
        if(schedule != null) {
            schedule.setHeadline(newScheduleDto.getHeadline());
            schedule.setContext(newScheduleDto.getContext());
            schedule.setDate(newScheduleDto.getDate());
            schedule.setUserId(newScheduleDto.getUserId());
        }
        return schedule;
    }


    @Transactional
    public Schedule delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElse(null);

        if (schedule != null) {
            scheduleRepository.delete(schedule);
        }

        return schedule;
    }


}
