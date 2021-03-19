package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Command {

    private String prefix;
    private String label;
    private ArrayList<String> aliass;
    private MessageEmbed help;

    public Command() {
        aliass = new ArrayList<>();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MessageEmbed getHelp() {
        return help;
    }

    public void setHelp(MessageEmbed help) {
        this.help = help;
    }

    public abstract MessageEmbed setHelp();

    public abstract void executerCommande(MessageReceivedEvent event);

    protected void addAlias (String alias) {
        aliass.add(alias);
    }

    public boolean isAlias(String alias) { return aliass.contains(alias); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return prefix.equals(command.prefix) &&
                label.equals(command.label) &&
                Objects.equals(help, command.help);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, label, help);
    }
}
