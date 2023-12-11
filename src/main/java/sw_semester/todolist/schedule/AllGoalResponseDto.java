package sw_semester.todolist.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllGoalResponseDto {
    private Long year;
    private Long month;
    private String yearGoal;
    private String monthGoal;
    private Boolean yearIsDone;
    private Boolean monIsDone;
    public AllGoalResponseDto(Long year, Long month, String yearGoal, String monthGoal, Boolean yearIsDone, Boolean monIsDone){
        this.year = year;
        this.month = month;
        this.yearGoal = yearGoal;
        this.monthGoal = monthGoal;
        this.yearIsDone=yearIsDone;
        this.monIsDone=monIsDone;
    }

}
