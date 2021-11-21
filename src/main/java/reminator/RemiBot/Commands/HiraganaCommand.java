package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;

public class HiraganaCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.JEU;
    }

    @Override
    public String getPrefix() {
        return Command.super.getPrefix();
    }

    @Override
    public String getLabel() {
        return "hiragana";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Le premier qui trouve 10 hiragana gagne la partie !";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        long channelId = channel.getIdLong();
        Map<User, Integer> scores = new HashMap<>();

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Hiragana game ! Le premier qui trouve 10 hiragana gagne la partie !").setDescription("Que le meilleur gagne !");
        EnvoiMessage.sendMessage(event,embedBuilder.build());
        Hiragana hiragana = Hiragana.getRandom();

        embedBuilder = new EmbedBuilder().setTitle(hiragana.toString()).setDescription("Comment écrit-on ce hiragana en rômaji ?");


        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;

                String msg = event.getMessage().getContentRaw();
                User user = event.getAuthor();

                if(hiragana.getName().equalsIgnoreCase(msg)) {
                    EnvoiMessage.sendMessage(event, "** Bravo " + user.getAsMention() + " :partying_face:\n" + hiragana.toString() + " se lit bien " + hiragana.getName());
                    int score;
                    if (scores.containsKey(user)) {
                        score = scores.get(user) + 1;
                        scores.replace(user, score);
                    } else {
                        score = 1;
                        scores.put(user, score);
                    }
                    EnvoiMessage.sendMessage(event, user.getAsMention() + " a maintenant " + score + " points !");
                    if (score == 10) {
                        EnvoiMessage.sendMessage(event, user.getAsMention() + " a gagné !!! :partying_face: :partying_face: :partying_face:");
                        EmbedBuilder classement = new EmbedBuilder().setTitle("Classement des joueurs de la partie");
                        StringBuilder s = new StringBuilder();
                        for(Map.Entry<User, Integer> e : scores.entrySet()) {
                            s.append(e.getKey().getAsMention()).append(" : ").append(e.getValue()).append("\n");
                        }
                        classement.addField("Test", s.toString(), false);
                        event.getJDA().removeEventListener(this);
                    }

                    return;
                }

                if(msg.equalsIgnoreCase("r!hiragana")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }
            }
        });
    }
}
