package sw_semester.todolist.Schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import sw_semester.todolist.domain.Schedule;
import sw_semester.todolist.domain.User;

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
    private LocalTime time;
    private Boolean isRepeat;
    private LocalDate repeatEndDate;
    private Set<DayOfWeek> daysOfWeek;
    private Set<String> interests;
    private String tags;


    public boolean Repeat() {
        return isRepeat;
    }

    public Boolean getIsDone() {
        return isDone != null ? isDone : false;
    }
}