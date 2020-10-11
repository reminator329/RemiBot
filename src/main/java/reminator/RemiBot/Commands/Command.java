package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import reminator.RemiBot.Categories.Categorie;

public abstract class Command {

    private String prefix;
    private String label;
    private MessageEmbed help;

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

    public abstract void executerCommande(GuildMessageReceivedEvent event);
}
