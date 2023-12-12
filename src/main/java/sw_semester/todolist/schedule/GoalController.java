package sw_semester.todolist.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.MonGoal;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/createYearGoal")
    public YearGoalDto yearGoalCreate(@RequestBody YearGoalDto yearGoalDto, @AuthenticationPrincipal User user) {
        return goalService.createYGoal(yearGoalDto, user);
    }

    @PostMapping("/createMonGoal")
    public MonGoalDto monGoalCreate(@RequestBody MonGoalDto monGoalDto, @AuthenticationPrincipal User user) {
        return goalService.createMGoal(monGoalDto, user);
    }

    @PutMapping("/updateYearGoal/{id}")
    public ResponseEntity<YearGoalDto> updateYearGoal(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean isDone,
            @RequestBody Map<String, String> requestBody,
            @AuthenticationPrincipal User user) {
        String updatedYearGoal = requestBody.get("updatedYearGoal");
        String isDoneString = (String) requestBody.get("isDone");
        Boolean Done = Boolean.valueOf(isDoneString);
        YearGoalDto updatedDto = goalService.updateYGoal(id, updatedYearGoal, Done, user);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/updateMonGoal/{id}")
    public ResponseEntity<MonGoalDto> updateMonGoal(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean isDone,
            @RequestBody Map<String, String> requestBody,
            @AuthenticationPrincipal User user) {
        String updatedMonGoal = requestBody.get("updatedMonGoal");
        String isDoneString = (String) requestBody.get("isDone");
        Boolean Done = Boolean.valueOf(isDoneString);
        MonGoalDto updatedDto = goalService.updateMGoal(id, updatedMonGoal, Done, user);
        return ResponseEntity.ok(updatedDto);
    }


    @GetMapping("/findYearGoals")
    public ResponseEntity<List<YearGoalDto>> findYearGoals(@RequestParam Long year, @RequestParam Long userId) {
        List<YearGoal> yearGoals = goalService.findByYear(year, userId);
        return ResponseEntity.ok(convertToYearGoalDtoList(yearGoals));
    }

    @GetMapping("/findMonthGoals")
    public ResponseEntity<List<MonGoalDto>> findMonthGoals(@RequestParam Long month, @RequestParam Long userId) {
        List<MonGoal> monthGoals = goalService.findByMonth(month, userId);
        return ResponseEntity.ok(convertToMonGoalDtoList(monthGoals));
    }
    private List<YearGoalDto> convertToYearGoalDtoList(List<YearGoal> yearGoals) {
        return yearGoals.stream()
                .map(yearGoal -> {
                    YearGoalDto dto = new YearGoalDto();
                    dto.setId(yearGoal.getId());
                    dto.setYear(yearGoal.getYear());
                    dto.setYearGoal(yearGoal.getYearGoal());
                    dto.setIsDone(yearGoal.getIsDone());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<MonGoalDto> convertToMonGoalDtoList(List<MonGoal> monthGoals) {
        return monthGoals.stream()
                .map(monGoal -> {
                    MonGoalDto dto = new MonGoalDto();
                    dto.setId(monGoal.getId());
                    dto.setYear(monGoal.getYear());
                    dto.setMonth(monGoal.getMonth());
                    dto.setMonGoal(monGoal.getMonGoal());
                    dto.setIsDone(monGoal.getIsDone());
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @DeleteMapping("/deleteYearGoal/{id}")
    public ResponseEntity<String> deleteYearGoal(@PathVariable Long id, @AuthenticationPrincipal User user) {
        goalService.deleteYearGoal(id);
        return ResponseEntity.ok("YearGoal deleted successfully");
    }

    @DeleteMapping("/deleteMonGoal/{id}")
    public ResponseEntity<String> deleteMonGoal(@PathVariable Long id, @AuthenticationPrincipal User user) {
        goalService.deleteMonGoal(id);
        return ResponseEntity.ok("MonGoal deleted successfully");
    }

    @GetMapping("/getYearAndMonthGoal")
    public ResponseEntity<List<AllGoalResponseDto>> getYearAndMonthGoal(
            @RequestParam Long year,
            @RequestParam Long userId) {
        try {
            List<AllGoalResponseDto> goals = goalService.getYearAndMonthGoal(year, userId);
            return ResponseEntity.ok(goals);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
