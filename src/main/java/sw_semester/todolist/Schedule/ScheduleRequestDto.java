package sw_semester.todolist.Schedule;

import lombok.*;
import sw_semester.todolist.domain.Schedule;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    private LocalDate date;
    private String headline;
    private String context;
    private Boolean isDone;
    private Long userId;

    public ScheduleRequestDto(LocalDate date, String headline, String context, Boolean isDone, Long userId){
        this.context=context;
        this.date=date;
        this.headline=headline;
        this.isDone=isDone;
        this.userId=userId;
    }

}