package reminator.RemiBot.commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.Japonais.enums.kanas.Katakana;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KatakanaCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.JAPONAIS;
    }

    @Override
    public String getPrefix() {
        return Command.super.getPrefix();
    }

    @Override
    public String getLabel() {
        return "katakana";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Le premier qui trouve 10 katakana gagne la partie !";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();

        long channelId = channel.getIdLong();
        Map<User, Integer> scores = new HashMap<>();

        final EmbedBuilder[] embedBuilder = {new EmbedBuilder().setTitle("Katakana game ! Le premier qui trouve 10 katakana gagne la partie !").setDescription("Que le meilleur gagne !")};
        new EnvoiMessage().sendMessage(event, embedBuilder[0].build());
        final Katakana[] katakana = {Katakana.getRandom()};

        embedBuilder[0] = new EmbedBuilder().setTitle(katakana[0].japonais()).setDescription("Comment écrit-on ce katakana en rômaji ?");
        new EnvoiMessage().sendMessage(event, embedBuilder[0].build());


        RemiBot.api.addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;
                if (event.getChannel().getIdLong() != channelId) return;

                String msg = event.getMessage().getContentRaw();
                User user = event.getAuthor();

                if(katakana[0].roomaji().equalsIgnoreCase(msg)) {
                    new EnvoiMessage().sendMessage(event, "** Bravo " + user.getAsMention() + "** :partying_face:\n" + katakana[0].japonais() + " se lit bien " + katakana[0].roomaji());
                    int score;
                    if (scores.containsKey(user)) {
                        score = scores.get(user) + 1;
                        scores.replace(user, score);
                    } else {
                        score = 1;
                        scores.put(user, score);
                    }
                    new EnvoiMessage().sendMessage(event, user.getAsMention() + " a maintenant " + score + " points !");
                    if (score == 10) {
                        new EnvoiMessage().sendMessage(event, user.getAsMention() + " a gagné !!! :partying_face: :partying_face: :partying_face:");
                        EmbedBuilder classement = new EmbedBuilder().setTitle("Classement des joueurs de la partie");
                        StringBuilder s = new StringBuilder();
                        for(Map.Entry<User, Integer> e : scores.entrySet()) {
                            s.append(e.getKey().getAsMention()).append(" : ").append(e.getValue()).append("\n");
                        }
                        classement.addField("Test", s.toString(), false);
                        new EnvoiMessage().sendMessage(event, classement.build());
                        event.getJDA().removeEventListener(this);
                    } else {
                        katakana[0] = Katakana.getRandom();
                        embedBuilder[0] = new EmbedBuilder().setTitle(katakana[0].japonais()).setDescription("Comment écrit-on ce katakana en rômaji ?");
                        new EnvoiMessage().sendMessage(event, embedBuilder[0].build());
                    }
                    return;
                }

                if(msg.equalsIgnoreCase("r!katakana")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }
            }
        });
    }
}
