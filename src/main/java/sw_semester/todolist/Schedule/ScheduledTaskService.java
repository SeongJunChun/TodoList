package sw_semester.todolist.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import sw_semester.todolist.domain.Schedule;

public class ScheduledTaskService {
    @Autowired
    private TaskScheduler taskScheduler;

    public void scheduleRecurringTask(Schedule schedule) {
        // 알람 설정 및 요일 반복 로직 추가
    }
}
