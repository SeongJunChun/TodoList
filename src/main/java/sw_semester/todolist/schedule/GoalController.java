package sw_semester.todolist.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sw_semester.todolist.domain.MonGoal;
import sw_semester.todolist.domain.User;
import sw_semester.todolist.domain.YearGoal;

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
            @RequestBody Map<String, String> requestBody,
            @AuthenticationPrincipal User user) {
        String updatedYearGoal = requestBody.get("updatedYearGoal");
        YearGoalDto updatedDto = goalService.updateYGoal(id, updatedYearGoal, user);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/updateMonGoal/{id}")
    public ResponseEntity<MonGoalDto> updateMonGoal(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody,
            @AuthenticationPrincipal User user) {
        String updatedMonGoal = requestBody.get("updatedMonGoal");
        MonGoalDto updatedDto = goalService.updateMGoal(id, updatedMonGoal, user);
        return ResponseEntity.ok(updatedDto);
    }


    @GetMapping("/findYearGoals")
    public ResponseEntity<List<YearGoalDto>> findYearGoals(@RequestParam Long year, @AuthenticationPrincipal User user) {
        List<YearGoal> yearGoals = goalService.findByYear(year, user);
        return ResponseEntity.ok(convertToYearGoalDtoList(yearGoals));
    }

    @GetMapping("/findMonthGoals")
    public ResponseEntity<List<MonGoalDto>> findMonthGoals(@RequestParam Long month, @AuthenticationPrincipal User user) {
        List<MonGoal> monthGoals = goalService.findByMonth(month, user);
        return ResponseEntity.ok(convertToMonGoalDtoList(monthGoals));
    }
    private List<YearGoalDto> convertToYearGoalDtoList(List<YearGoal> yearGoals) {
        return yearGoals.stream()
                .map(yearGoal -> new YearGoalDto(yearGoal.getYear(), yearGoal.getYearGoal()))
                .collect(Collectors.toList());
    }

    private List<MonGoalDto> convertToMonGoalDtoList(List<MonGoal> monthGoals) {
        return monthGoals.stream()
                .map(monGoal -> new MonGoalDto(monGoal.getYear(), monGoal.getMonth(), monGoal.getMonGoal()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteYearGoal/{id}")
    public ResponseEntity<String> deleteYearGoal(@PathVariable Long id, @AuthenticationPrincipal User user) {
        goalService.deleteYearGoal(id, user);
        return ResponseEntity.ok("YearGoal deleted successfully");
    }

    @DeleteMapping("/deleteMonGoal/{id}")
    public ResponseEntity<String> deleteMonGoal(@PathVariable Long id, @AuthenticationPrincipal User user) {
        goalService.deleteMonGoal(id, user);
        return ResponseEntity.ok("MonGoal deleted successfully");
    }
}
