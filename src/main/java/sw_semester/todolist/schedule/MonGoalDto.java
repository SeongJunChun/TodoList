package sw_semester.todolist.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw_semester.todolist.domain.MonGoal;

@Data
@NoArgsConstructor
public class MonGoalDto {
    private Long id;
    private Long year;
    private Long month;
    private String monGoal;
    private Boolean isDone;

    public MonGoalDto (Long id, Long year, Long month, String monGoal, Boolean isDone){
        this.id=id;
        this.monGoal=monGoal;
        this.month=month;
        this.year=year;
        this.isDone=isDone;
    }
}
