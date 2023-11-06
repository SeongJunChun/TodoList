package sw_semester.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw_semester.todolist.Schedule.ScheduleRequestDto;
import sw_semester.todolist.Schedule.ScheduleResponseDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
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

    public Schedule(){}

    public Schedule(ScheduleRequestDto requestDto){
        this.isDone=requestDto.getIsDone();
        this.date = LocalDate.parse(requestDto.getDate());
        this.userId=requestDto.getUserId();
        this.headline=requestDto.getHeadline();
        this.context=requestDto.getContext();
    }

    public Schedule(Boolean isDone, LocalDate date, Long userId, String headline, String context){
        this.isDone=isDone;
        this.date = date;
        this.userId=userId;
        this.headline=headline;
        this.context=context;
    }

    public void update(ScheduleRequestDto requestDto){
        this.isDone=requestDto.getIsDone();
        this.date = LocalDate.parse(requestDto.getDate());
        this.userId=requestDto.getUserId();
        this.headline=requestDto.getHeadline();
        this.context=requestDto.getContext();
    }

}
