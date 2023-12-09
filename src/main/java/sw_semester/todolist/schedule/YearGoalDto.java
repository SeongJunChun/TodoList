package sw_semester.todolist.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class YearGoalDto {
    private Long year;
    private String yearGoal;

    public YearGoalDto (Long year, String yearGoal){
        this.yearGoal=yearGoal;
        this.year=year;
    }
}
