package sw_semester.todolist.schedule;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YearGoalDto {
    private Long id;
    private Long year;
    private String yearGoal;
    private Boolean isDone;

    public YearGoalDto (Long id, Long year, String yearGoal,Boolean isDone){
        this.id=id;
        this.yearGoal=yearGoal;
        this.year=year;
        this.isDone=isDone;
    }

}
