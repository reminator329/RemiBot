package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

public interface Reminder {
    LocalTime getTime();
    CompletableFuture<String> getMessage();
    String getUserId();
}
