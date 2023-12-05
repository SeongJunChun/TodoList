package sw_semester.todolist.Schedule;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private LocalDate date;
    private  String headline;
    private  String context;
    private Boolean isDone;
    private LocalTime time;
    private Boolean isRepeat;

    public ScheduleResponseDto(Long id, LocalDate date, String headline, String context, Boolean isDone, Boolean isRepeat, LocalTime time) {
        this.id = id;
        this.date = date;
        this.headline = headline;
        this.context = context;
        this.isDone = isDone;
        this.isRepeat = isRepeat;
        this.time = time;
    }

    public String getFormattedDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return date.format(dateFormat);
    }
}
