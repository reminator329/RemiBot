package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Commands extends ListenerAdapter {

    private final ArrayList<Command> commands = new ArrayList<>();

    public Commands() {
        commands.add(new PingCommand());
        commands.add(new AlbumCommand());
        commands.add(new EcouteBilalCommand());
        commands.add(new SpamCommand());
        commands.add(new HelpCommand(this));
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        int n = 1 + (int)(Math.random() * ((10 - 1) + 1));
        if (n == 5) {
            for (Member m : event.getGuild().getMembers()) {
                if (m.getUser().getAsTag().equalsIgnoreCase("Elorya#5162")) {
                    event.getChannel().sendMessage(m.getUser().getAsMention()).queue();
                }
            }
        }

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        for (Command c : commands) {
            String prefixLabel = c.getPrefix() + c.getLabel();
            if (prefixLabel.equalsIgnoreCase(args[0])) {
                c.executerCommande(event);
            }
        }
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(this.commands);
    }
}