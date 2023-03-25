package reminator.RemiBot.commands;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SpamCommand implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    Timer timer;

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "spam";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Permet d'envoyer un message toutes les 2 secondes.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        if (ecoutes.contains(channel)) {
            channel.sendMessage("Arrêt du spam").queue();
            ecoutes.remove(channel);
            timer.cancel();
            timer.purge();
        } else {
            ecoutes.add(channel);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (event.isFromGuild())
                        new EnvoiMessage().sendMessage(event, "Je spam");
                    else
                        new EnvoiMessage().sendPrivate(author, "Je spam");
                }
            }, 0, 2500);
        }
    }
}
