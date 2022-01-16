package reminator.RemiBot.utils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EnvoiMessage {

    private EnvoiMessage() {}

    public static void sendMessage(MessageReceivedEvent event, MessageEmbed messageEmbed) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), messageEmbed);
        } else {
            sendPrivate(event.getAuthor(), messageEmbed);
        }
    }

    public static void sendMessage(MessageReceivedEvent event, String message) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), message);
        } else {
            sendPrivate(event.getAuthor(), message);
        }
    }

    public static void sendPrivate(User user, String message) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(message))
                .queue();
    }

    public static void sendPrivate(User user, MessageEmbed messageEmbed) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(messageEmbed))
                .queue();
    }

    public static void sendGuild(MessageChannel messageChannel, String message) {
        messageChannel.sendMessage(message).queue();
    }

    public static void sendGuild(MessageChannel messageChannel, MessageEmbed messageEmbed) {
        messageChannel.sendMessageEmbeds(messageEmbed).queue();
    }
}
