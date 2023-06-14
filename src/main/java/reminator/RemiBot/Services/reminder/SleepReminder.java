package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.List;

public class SleepReminder implements Reminder {
    @Override
    public LocalTime getTime() {
        return LocalTime.of(21, 45, 0, 0);
    }

    @Override
    public List<String> getMessages() {
        return List.of(
                "Il est l'heure d'aller dormir !",
                "Au lit !",
                "Vas dormir vite !",
                "OHHH! Oh lit !"
        );
    }

    @Override
    public String getUserId() {
        return "264490592610942976";
    }
}
