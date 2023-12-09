package sw_semester.todolist.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;

import java.time.LocalDate;
import java.util.*;
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
    public ScheduleCreateResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal User user) {
        return scheduleService.create(requestDto, user);
    }


    @GetMapping("/scheduleByDate")
    public Map<String, Object> getScheduleByDate(@RequestParam(name = "selectedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        List<Schedule> schedules = scheduleService.findByDate(selectedDate);
        Map<String, Object> response = new HashMap<>();

        if (!schedules.isEmpty()) {
            List<Map<String, Object>> scheduleInfoList = schedules.stream()
                    .map(schedule -> {
                        Map<String, Object> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("headline", schedule.getHeadline());
                        scheduleInfo.put("context",schedule.getContext());
                        scheduleInfo.put("time",schedule.getTime());
                        scheduleInfo.put("isRepeat",schedule.getIsRepeat());
                        scheduleInfo.put("repeatDay",schedule.getDaysOfWeek());
                        scheduleInfo.put("repeatEnd",schedule.getRepeatEndDate());
                        scheduleInfo.put("interest",schedule.getInterests());
                        scheduleInfo.put("tag",schedule.getTags());
                        scheduleInfo.put("isDone", schedule.getIsDone());
                        scheduleInfo.put("scheduleId",schedule.getId());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());

            response.put("schedules", scheduleInfoList);
        }
        return response;
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleRequestDto requestDto, @PathVariable("id") Long id,@AuthenticationPrincipal User user) {
        try {
            scheduleService.update(id, requestDto,user);
            return ResponseEntity.ok("Schedule updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating schedule");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("id") Long id, @AuthenticationPrincipal User use) {
        try {
            scheduleService.delete(id);
            return ResponseEntity.ok("Schedule deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting schedule");
        }
    }

    @GetMapping("/findByInterests")
    public Map<String, Object> getScheduleByInterests(@RequestParam(name = "interests") Set<String> interests) {
        List<Schedule> schedules = scheduleService.findByInterests(interests);
        Map<String, Object> response = new HashMap<>();

        if (!schedules.isEmpty()) {
            List<Map<String, Object>> scheduleInfoList = schedules.stream()
                    .map(schedule -> {
                        Map<String, Object> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("headline", schedule.getHeadline());
                        scheduleInfo.put("username", schedule.getUser().getUsername());
                        scheduleInfo.put("profile",schedule.getUser().getProfileImageUrl());
                        scheduleInfo.put("userId",schedule.getUser().getId());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());

            response.put("schedules", scheduleInfoList);
        }
        return response;
    }


    @GetMapping("/findByTags")
    public Map<String, Object> getScheduleByTags(@RequestParam(name = "tags") Set<String> tags) {
        List<Schedule> schedules = scheduleService.findByTags(tags);
        Map<String, Object> response = new HashMap<>();

        if (!schedules.isEmpty()) {
            Set<Long> uniqueScheduleIds = new HashSet<>();
            List<Map<String, Object>> scheduleInfoList = schedules.stream()
                    .filter(schedule -> uniqueScheduleIds.add(schedule.getId()))
                    .map(schedule -> {
                        Map<String, Object> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("headline", schedule.getHeadline());
                        scheduleInfo.put("user", schedule.getUser().getUsername());
                        scheduleInfo.put("profile",schedule.getUser().getProfileImageUrl());
                        scheduleInfo.put("userId",schedule.getUser().getId());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());

            response.put("schedules", scheduleInfoList);
        }
        return response;
    }

    @GetMapping("/findAllSchedules")
    public Map<String, Object> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        Map<String, Object> response = new HashMap<>();

        if (!schedules.isEmpty()) {
            List<Map<String, Object>> scheduleInfoList = schedules.stream()
                    .map(schedule -> {
                        Map<String, Object> scheduleInfo = new HashMap<>();
                        scheduleInfo.put("headline", schedule.getHeadline());
                        scheduleInfo.put("username", schedule.getUser().getUsername());
                        scheduleInfo.put("profile", schedule.getUser().getProfileImageUrl());
                        scheduleInfo.put("userId", schedule.getUser().getId());
                        return scheduleInfo;
                    })
                    .collect(Collectors.toList());

            response.put("schedules", scheduleInfoList);
        }
        return response;
    }



}
