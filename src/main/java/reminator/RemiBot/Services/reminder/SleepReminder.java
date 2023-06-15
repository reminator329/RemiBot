package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static reminator.RemiBot.Services.OpenAIService.openAI;

public class SleepReminder implements Reminder {
    @Override
    public LocalTime getTime() {
        return LocalTime.of(21, 45, 0, 0);
    }

    @Override
    public CompletableFuture<String> getMessage() {
        return openAI().generate("Tu es un bot Discord chargé de s'assurer que les membres vont se coucher à une heure raisonnable. Écris un message bref et informel pour prévenir Quentin qu'il est l'heure de dormir.").thenApply(s -> s.replaceAll("Quentin",  " <@"+getUserId()+">"));
    }

    @Override
    public String getUserId() {
        return "264490592610942976";
    }
}
