package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.util.Timer;
import java.util.TimerTask;

public class Spam extends Command {

    private static boolean execute = false;
    Timer timer;

    public Spam() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("spam");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        return null;
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        if (execute) {
            channel.sendTyping().queue();
            channel.sendMessage("Arrêt de la commande").queue();
            timer.cancel();
            timer.purge();
            execute = false;
        } else {
            execute = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    channel.sendTyping().queue();
                    channel.sendMessage("Je spam").queue();
                }
            }, 0, 2500);
        }
    }
}