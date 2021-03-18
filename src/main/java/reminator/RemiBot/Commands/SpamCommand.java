package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SpamCommand extends Command {

    private static boolean execute = false;
    Timer timer;

    public SpamCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("spam");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande spam");
        builder.appendDescription("Permet d'envoyer un message toutes les 2 secondes.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.");
        builder.addField("Signature", "`r!spam`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        MessageChannel channel = event.getChannel();

        if (execute) {
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
                    channel.sendMessage("Je spam").queue();
                }
            }, 0, 2500);
        }
    }
}
