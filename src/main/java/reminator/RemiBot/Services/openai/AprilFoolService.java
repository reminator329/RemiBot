package reminator.RemiBot.Services.openai;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static reminator.RemiBot.Services.openai.OpenAIClient.format;

public class AprilFoolService extends ListenerAdapter {
    private final OpenAIClient openai = new OpenAIClient(System.getenv("OPENAI_TOKEN"));

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        String contentRaw = format(event.getMessage().getContentRaw());

        if (!contentRaw.endsWith("?")) {
            return;
        }


        try {
            String completed = openai.complete(contentRaw);
            completed = completed.replace(contentRaw, "");

            event.getMessage().reply(completed).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
