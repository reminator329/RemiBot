package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.util.List;

public class SleepReminder2 implements Reminder {
    @Override
    public LocalTime getTime() {
        return LocalTime.of(22, 0, 0, 0);
    }

    @Override
    public List<String> getMessages() {
        return List.of(
                ":exploding_head:",
                "WHOOOOO!!",
                ":angry!",
                "AU LIT !",
                "OHHH! Oh lit !",
                "DORS!"
        );
    }

    @Override
    public String getUserId() {
        return "264490592610942976";
    }
}
