package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class PingCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "ping";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"p"};
    }

    @Override
    public String getDescription() {
        return "Répond pong et affiche la musique écoutée actuellement sur spotify.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        EmbedBuilder builder = BotEmbed.SPOTIFY.getBuilder(event.getMember());
        builder.setTitle("Pong !");

        EnvoiMessage.sendMessage(event, builder.build());

        System.out.println(args);

        if (args.size() > 0 && args.get(0).equalsIgnoreCase("true")) {
            for (Member member: event.getGuild().getMembers()) {
                EnvoiMessage.sendMessage(event, "**" + member.getEffectiveName() + "**");
                EnvoiMessage.sendMessage(event, member.getActiveClients().toString());
                EnvoiMessage.sendMessage(event, member.getOnlineStatus().toString());
                EnvoiMessage.sendMessage(event, member.getRoles().toString());
                EnvoiMessage.sendMessage(event, member.getActivities().toString());

            }
        }
    }
}
