package reminator.RemiBot.Services.repondeur;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepondeurService extends ListenerAdapter {



    private JDA api;

    public RepondeurService(JDA api) {
        this.api = api;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

//        if(event.getGuild().getId().equals("427560908643106816")) {
//            return;
//        }

        List<String> args = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split("\\s+")));

        for (String arg : args) {

            Pattern patterndi = Pattern.compile("(?i)(di.*)");
            Matcher matcherdi = patterndi.matcher(arg);

            Pattern patterndy = Pattern.compile("(?i)(dy.*)");
            Matcher matcherdy = patterndy.matcher(arg);

            Pattern patterncri = Pattern.compile("(?i)(cri.*)");
            Matcher matchercri = patterncri.matcher(arg);

            if (matcherdi.find() && matcherdi.group(1).length() > 2) {
                Random random = new Random();
                int r = random.nextInt(100);
                if (r <= 10) {
                    String message = matcherdi.group(1).substring(2);
                    new EnvoiMessage().sendMessage(event, message.toLowerCase());
                }
            }
            if (matcherdy.find() && matcherdy.group(1).length() > 2) {
                Random random = new Random();
                int r = random.nextInt(100);
                if (r <= 10) {
                    String message = matcherdy.group(1).substring(2);
                    new EnvoiMessage().sendMessage(event, message.toLowerCase());
                }
            }
            if (matchercri.find() && matchercri.group(1).length() > 3) {
                Random random = new Random();
                int r = random.nextInt(100);
                if (r <= 10) {
                    String message = matchercri.group(1).substring(3);
                    new EnvoiMessage().sendMessage(event, message.toUpperCase());
                }
            }
        }
    }
}
