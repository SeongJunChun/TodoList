package sw_semester.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw_semester.todolist.Schedule.ScheduleRequestDto;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isDone;

    @Column
    private LocalDate date;

    @Column
    private Long userId;

    @Column
    private String headline;

    @Column
    private String context;

    public Schedule(ScheduleRequestDto requestDto){
        this.isDone = requestDto.getIsDone();
        this.context = requestDto.getContext();
        this.date = requestDto.getDate();
        this.userId=requestDto.getUserId();
        this.headline = requestDto.getHeadline();
    }

}