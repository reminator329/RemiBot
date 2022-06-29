package reminator.RemiBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.enums.Gif;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class PongCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "pong";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"po"};
    }

    @Override
    public String getDescription() {
        return "Répond pong, affiche la musique écoutée actuellement sur spotify et envoie un gif en MP.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        Member member = event.getMember();
        User author = event.getAuthor();

        EmbedBuilder builder = BotEmbed.SPOTIFY.getBuilder(member);
        builder.setTitle("Ping !");

        EnvoiMessage.sendMessage(event, builder.build());
        EnvoiMessage.sendPrivate(author, Gif.getRandom().getUrl());
    }
}
