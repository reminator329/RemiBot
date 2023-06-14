package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.util.List;

public interface Reminder {
    LocalTime getTime();
    List<String> getMessages();
    String getUserId();
}
