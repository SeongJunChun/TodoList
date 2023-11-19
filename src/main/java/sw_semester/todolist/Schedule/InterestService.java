package sw_semester.todolist.Schedule;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class InterestService {
    private List<String> interests;

    public InterestService() {
        this.interests = new ArrayList<>(Arrays.asList("주식", "어학", "수능", "재테크", "운동", "공무원", "공부", "취업"));
    }

    public List<String> getAllInterests() {
        return interests;
    }

    public void addInterest(String interest) {
        interests.add(interest);
    }

    public void removeInterest(String interest) {
        interests.remove(interest);
    }
}
