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

    public YearGoalDto (Long id, Long year, String yearGoal){
        this.id=id;
        this.yearGoal=yearGoal;
        this.year=year;
    }
}
