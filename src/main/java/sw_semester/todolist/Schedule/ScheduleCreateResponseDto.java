package sw_semester.todolist.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sw_semester.todolist.domain.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class ScheduleCreateResponseDto {
    private Long id;
    private String context;
    private LocalDate date;
    //private User user;
    private Long userId;
    private String headline;
    private LocalTime time;
    private Boolean isRepeat;
    private Set<DayOfWeek> daysOfWeek;
    private LocalDate repeatEndDate;
    private Set<String> interests;
    private String tags;

    public ScheduleCreateResponseDto(Long id, String headline, String context, Boolean isRepeat, LocalDate repeatEndDate, LocalDate date, LocalTime time, String tags, Set<String> interests, User user, Set<DayOfWeek> daysOfWeek) {
        this.id = id;
        this.date = date;
        this.repeatEndDate = repeatEndDate;
        this.interests = interests;
        this.daysOfWeek = daysOfWeek;
        this.time = time;
        this.tags = tags;
        this.userId = user.getId();
        //this.user=user;
        this.isRepeat = isRepeat;
        this.context=context;
        this.headline = headline;
    }
}
