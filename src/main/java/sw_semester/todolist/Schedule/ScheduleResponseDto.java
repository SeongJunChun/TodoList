package sw_semester.todolist.Schedule;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleResponseDto {
    private Long id;
    private LocalDate date;
    private  String headline;
    private  String context;
    private Boolean isDone;

    public ScheduleResponseDto(Long id, LocalDate date, String headline, String context, Boolean isDone) {
        this.id = id;
        this.date = date;
        this.headline = headline;
        this.context = context;
        this.isDone = isDone;
    }

    public String getDateAsString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateFormat);
    }

    public String getFormattedDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return date.format(dateFormat);
    }
}
