package reminator.RemiBot.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

import java.util.ArrayList;
import java.util.List;

public class EnvoiMessage {

    private final List<List<ItemComponent>> components;

    public EnvoiMessage() {
         components = new ArrayList<>();
    }

    public void sendMessage(CommandExecutedEvent event, MessageEmbed messageEmbed) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), messageEmbed);
        } else {
            sendPrivate(event.getAuthor(), messageEmbed);
        }
    }

    public void sendMessage(CommandExecutedEvent event, String message) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), message);
        } else {
            sendPrivate(event.getAuthor(), message);
        }
    }

    public void sendMessage(MessageReceivedEvent event, String message) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), message);
        } else {
            sendPrivate(event.getAuthor(), message);
        }
    }

    public void sendMessage(MessageReceivedEvent event, MessageEmbed messageEmbed) {
        if (event.isFromGuild()) {
            sendGuild(event.getChannel(), messageEmbed);
        } else {
            sendPrivate(event.getAuthor(), messageEmbed);
        }
    }

    public void sendPrivate(User user, String message) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(message))
                .queue();
    }

    public void sendPrivate(User user, MessageEmbed messageEmbed) {
        user.openPrivateChannel()
                .flatMap(channel -> channel
                        .sendMessageEmbeds(messageEmbed)
                )
                .queue();
    }

    public void sendGuild(MessageChannel messageChannel, String message) {
        MessageCreateAction messageAction = messageChannel.sendMessage(message);
        addButtons(messageAction).queue();
    }

    public void sendGuild(MessageChannel messageChannel, MessageEmbed messageEmbed) {
        MessageCreateAction messageAction = messageChannel.sendMessageEmbeds(messageEmbed);
        addButtons(messageAction).queue();
    }

    private MessageCreateAction addButtons(MessageCreateAction message) {
        for(List<ItemComponent> component : components) {
            message = message.addActionRow(component);
        }
        return message;
    }

    public EnvoiMessage withComponents(List<List<ItemComponent>> components) {
        this.components.addAll(components);
        return this;
    }
}
