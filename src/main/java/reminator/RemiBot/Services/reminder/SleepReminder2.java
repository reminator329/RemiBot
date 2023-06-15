package reminator.RemiBot.Services.reminder;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static reminator.RemiBot.Services.OpenAIService.openAI;

public class SleepReminder2 implements Reminder {
    @Override
    public LocalTime getTime() {
        return LocalTime.of(22, 0, 0, 0);
    }

    @Override
    public CompletableFuture<String> getMessage() {
        return openAI().generate("Tu es un bot Discord chargé de s'assurer que les membres vont se coucher à une heure raisonnable. Tu as précédemment prévenu Quentin qu'il était l'heure de dormir mais il ne dort toujours pas. Écris à nouveau un message bref et informel mais sur un ton colérique cette fois !").thenApply(s -> s.replaceAll("Quentin",  " <@"+getUserId()+">"));
    }

    @Override
    public String getUserId() {
        return "264490592610942976";
    }
}
