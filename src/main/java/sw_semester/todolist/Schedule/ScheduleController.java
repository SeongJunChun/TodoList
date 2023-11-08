package sw_semester.todolist.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.Schedule;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    public Schedule createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        return scheduleService.create(requestDto);
    }

    @GetMapping("/scheduleList")
    public List<ScheduleResponseDto> getSchedulesList() {
        List<Schedule> schedules = scheduleService.findAll();
        return schedules.stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getDate(),
                        schedule.getHeadline(),
                        schedule.getContext(),
                        schedule.getIsDone()
                ))
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public Schedule getSchedule(@PathVariable("id") Long id) {
        return scheduleService.findOne(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleRequestDto requestDto, @PathVariable("id") Long id) {
        try {
            scheduleService.update(id, requestDto);
            return ResponseEntity.ok("Schedule updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating schedule");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }
}
