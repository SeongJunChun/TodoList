package sw_semester.todolist.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.Schedule;

import java.util.List;

@RestController
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
        return scheduleService.create(requestDto);
    }

    @GetMapping("/scheduleList")
    public List<Schedule> getSchedulesList() {
        return scheduleService.findAll();
    }


    @GetMapping("/{id}")
    public Schedule getSchedule(@PathVariable("id") Long id) {
        return scheduleService.findOne(id);
    }


    @PutMapping("/{id}")
    public Schedule updateSchedule(@RequestBody ScheduleRequestDto requestDto, @PathVariable("id") Long id) {
        Schedule schedule = new Schedule(requestDto);
        return scheduleService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteScheule(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }
}
