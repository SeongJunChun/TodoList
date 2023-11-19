package sw_semester.todolist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sw_semester.todolist.Schedule.ScheduleRequestDto;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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

    @Column
    private LocalTime time;

    @Column(name = "is_repeat")
    private Boolean isRepeat;

    @ElementCollection
    @CollectionTable(name = "schedule_days_of_week", joinColumns = @JoinColumn(name = "schedule_id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysOfWeek;

    @Column
    private LocalDate repeatEndDate;

    @ElementCollection
    @CollectionTable(name = "schedule_interests", joinColumns = @JoinColumn(name = "schedule_id"))
    private Set<String> interests;

    @Column
    private String tags;


    public Schedule(ScheduleRequestDto requestDto){
        this.isDone = requestDto.getIsDone();
        this.context = requestDto.getContext();
        this.date = requestDto.getDate();
        this.userId=requestDto.getUserId();
        this.headline = requestDto.getHeadline();
        this.time=requestDto.getTime();
        this.isRepeat=requestDto.getIsRepeat();
        this.repeatEndDate=requestDto.getRepeatEndDate();
        this.interests=requestDto.getInterests();
    }

}