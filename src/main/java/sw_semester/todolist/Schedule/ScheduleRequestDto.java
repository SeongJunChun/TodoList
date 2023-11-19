package sw_semester.todolist.Schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import sw_semester.todolist.domain.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private LocalDate date;
    private String headline;
    private String context;
    private Boolean isDone;
    private Long userId;
    private LocalTime time;
    private Boolean isRepeat;
    private LocalDate repeatEndDate;
    private Set<DayOfWeek> daysOfWeek;
    private Set<String> interests;
    private String tags;

    public ScheduleRequestDto(LocalDate date, String headline, String context, Boolean isDone, Long userId, LocalTime time, Boolean isRepeat, LocalDate repeatEndDate, Set<DayOfWeek> daysOfWeek, Set<String> interests, String tags){
        this.context=context;
        this.date=date;
        this.headline=headline;
        this.isDone=isDone;
        this.userId=userId;
        this.time=time;
        this.isRepeat=isRepeat;
        this.repeatEndDate=repeatEndDate;
        this.daysOfWeek = daysOfWeek;
        this.interests=interests;
        this.tags=tags;
    }

    public boolean Repeat() {
        return isRepeat;
    }
}