package reminator.RemiBot.commands.manager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandExecutedEvent {

    private final MessageReceivedEvent event;
    private final List<String> args;

    public CommandExecutedEvent(@NotNull MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    public User getAuthor() {
        return event.getAuthor();
    }

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public TextChannel getTextChannel() {
        return event.getChannel().asTextChannel();
    }

    public boolean isFromGuild() {
        return event.isFromGuild();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public Member getMember() {
        return event.getMember();
    }

    public List<String> getArgs() {
        return args;
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public JDA getJDA() {
        return event.getJDA();
    }
}
