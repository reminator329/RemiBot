package reminator.RemiBot.commands.manager;

import net.dv8tion.jda.api.entities.MessageEmbed;
import reminator.RemiBot.commands.enums.Category;
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

    void execute(CommandExecutedEvent event);

    default boolean isAlias(String alias) {
        return Arrays.stream(getAlliass()).collect(Collectors.toList()).contains(alias);
    }
}
