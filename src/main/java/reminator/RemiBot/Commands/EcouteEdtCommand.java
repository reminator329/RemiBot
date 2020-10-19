package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.edt.Edt;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EcouteEdtCommand extends Command {

    private static boolean execute = false;
    Timer timer;

    public EcouteEdtCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ecoute-edt");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ecoute-edt");
        builder.appendDescription("Permet d'envoyer les détails du prochain cours automatiquement'\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.");
        builder.addField("Signature", "`r!ecoute-bilal`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        if (execute) {
            channel.sendMessage("Arrêt de la commande").queue();
            timer.cancel();
            timer.purge();
            execute = false;
        } else {
            channel.sendMessage("Début de la commande").queue();
            execute = true;
            java.util.List<Member> members = event.getGuild().getMembers();

            timer = new Timer();
            final JSONObject[] cours = {null};
            final JSONObject[] pCours = {null};
            Edt edt = new Edt();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pCours[0] = edt.getNextCourse();
                    if (cours[0] == null || !pCours[0].toString().equals(cours[0].toString())) {
                        cours[0] = pCours[0];
                        edt.printCourse(cours[0], channel);
                    }
                }
            }, 0, 1000 * 60/*500 * 3600*/);
        }
    }
}
