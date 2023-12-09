package sw_semester.todolist.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw_semester.todolist.domain.MonGoal;

@Data
@NoArgsConstructor
public class MonGoalDto {
    private Long year;
    private Long month;
    private String monGoal;

    public MonGoalDto (Long year, Long month, String monGoal){
        this.monGoal=monGoal;
        this.month=month;
        this.year=year;
    }
}
