package reminator.RemiBot.utils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EnvoiMessage {

    private EnvoiMessage() {}

    public static void sendMessage(MessageReceivedEvent event, MessageEmbed messageEmbed) {
        System.out.println(event.isFromGuild());
        if (event.isFromGuild()) {
            event.getChannel().sendMessage(messageEmbed).queue();
        } else {
            event.getAuthor().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(messageEmbed))
                    .queue();
        }
    }

    public static void sendMessage(MessageReceivedEvent event, String message) {
        if (event.isFromGuild()) {
            event.getChannel().sendMessage(message).queue();
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
                .flatMap(channel -> channel.sendMessage(messageEmbed))
                .queue();
    }

    public static void sendGuild(MessageChannel messageChannel, String message) {
        messageChannel.sendMessage(message).queue();
    }

    public static void sendGuild(MessageChannel messageChannel, MessageEmbed messageEmbed) {
        messageChannel.sendMessage(messageEmbed).queue();
    }
}
