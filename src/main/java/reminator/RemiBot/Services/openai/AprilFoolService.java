package reminator.RemiBot.Services.openai;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static reminator.RemiBot.Services.openai.OpenAIClient.format;

public class AprilFoolService extends ListenerAdapter {
    private final OpenAIClient openai = new OpenAIClient(System.getenv("OPENAI_TOKEN"));

    private final Map<String, UserAIConv> convs = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        User author = event.getAuthor();
        if (author.isBot()) {
            return;
        }
        String contentRaw = format(event.getMessage().getContentRaw());

        Message lastMessage = event.getMessage().getReferencedMessage();

        UserAIConv conv = convs.computeIfAbsent(author.getId(), (k -> new UserAIConv()));

        if (lastMessage == null || conv.getLastBotMessage() == null || !lastMessage.getId().equals(conv.getLastBotMessage().getId())) {
            if (!contentRaw.endsWith("??")) {
                return;
            }
            contentRaw = contentRaw.substring(0, contentRaw.length()-1);
            conv.setText(contentRaw);
        } else {
            conv.addLine(contentRaw);
        }

        try {
            openai.complete(conv.text()).thenAccept(completed -> {
                try {
                    if (completed == null)
                        return;
                    String previousText = conv.text();
                    conv.setText(completed);
                    completed = completed.replace(previousText, "");
                    event.getMessage().reply(completed).queue(conv::setLastBotMessage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
