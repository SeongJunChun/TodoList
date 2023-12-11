package sw_semester.todolist.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import sw_semester.todolist.schedule.YearGoalDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
public class YearGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long year;
    @Column
    private String yearGaol;

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private Boolean isDone;

    public YearGoal(YearGoalDto yearGoalDto, User user){
        this.year=yearGoalDto.getYear();
        this.yearGaol=yearGoalDto.getYearGoal();
        this.user=user;
        this.id=yearGoalDto.getId();
        this.isDone = yearGoalDto.getIsDone();
    }

    public void setYearGoal(String yearGoal) {
        this.yearGaol=yearGoal;
    }
    public String getYearGoal() {
        return yearGaol;
    }
}
