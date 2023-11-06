package sw_semester.todolist.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.Schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/scheduleCreate")
    public Schedule createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/schedlueList")
    public List<ScheduleResponseDto> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleResponseDto> responseDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule.getId(), schedule.getDate(), schedule.getHeadline(), schedule.getContext(), schedule.getIsDone());
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }


    @GetMapping("{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule != null) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule.getId(), schedule.getDate(), schedule.getHeadline(), schedule.getContext(), schedule.getIsDone());
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
