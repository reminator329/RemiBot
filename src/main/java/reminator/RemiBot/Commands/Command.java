package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Command {

    default String getName() {
        return getLabel().replace('-', ' ');
    }

    Category getCategory();

    default String getPrefix() {
        return RemiBot.prefix;
    }

    String getLabel();

    String[] getAlliass();

    String getDescription();

    default MessageEmbed.Field[] getExtraFields() {
        return new MessageEmbed.Field[0];
    }

    default String getSignature() {
        return getPrefix() + getLabel();
    }

    default Color getColor() {
        return RemiBot.color;
    }

    void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args);

    default boolean isAlias(String alias) {
        return Arrays.stream(getAlliass()).collect(Collectors.toList()).contains(alias);
    }
}
