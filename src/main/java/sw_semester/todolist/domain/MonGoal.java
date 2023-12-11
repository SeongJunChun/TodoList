package sw_semester.todolist.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw_semester.todolist.schedule.MonGoalDto;
import sw_semester.todolist.schedule.YearGoalDto;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class MonGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Long year;

    @Column
    private Long month;

    @Column
    private String monGoal;

    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private Boolean isDone;


    public MonGoal(MonGoalDto monGoalDto, User user){
        this.month=monGoalDto.getMonth();
        this.monGoal=monGoalDto.getMonGoal();
        this.year=monGoalDto.getYear();
        this.user=user;
        this.id=monGoalDto.getId();
        this.isDone = monGoalDto.getIsDone();
    }

}
