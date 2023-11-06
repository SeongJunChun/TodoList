package sw_semester.todolist.Schedule;

import lombok.*;
import sw_semester.todolist.domain.Schedule;

@Getter
@Setter
public class ScheduleRequestDto {
    private String date;
    private String headline;
    private String context;
    private Boolean isDone;
    private Long userId;

   public ScheduleRequestDto(String date, String headline, String context, Boolean isDone, Long userId){
       this.context=context;
       this.date=date;
       this.headline=headline;
       this.isDone=isDone;
       this.userId=userId;
   }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

}
