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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EcouteBilalCommand extends Command {

    private static boolean execute = false;
    Timer timer;

    public EcouteBilalCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ecoute-bilal");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ecoute-bilal");
        builder.appendDescription("Permet de notifier les Bilal lorsque quelqu'un écoute une musique de Bilal Hassani.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.");
        builder.addField("Signature", "`r!ecoute-bilal`", false);
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
            channel.sendMessage("Début de la commande").queue();
            execute = true;
            List<Member> members = event.getGuild().getMembers();
            List<Role> roles = event.getGuild().getRoles();
            Role bilal = null;
            for (Role r : roles) {
                if (r.getId().equalsIgnoreCase("685892135614021651")) {
                    bilal = r;
                }
            }

            timer = new Timer();
            Role finalBilal = bilal;
            final String[] titre = new String[members.size()];
            Arrays.fill(titre, "");

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (int i=0; i<members.size(); i++) {
                        Member m = members.get(i);
                        List<Activity> activities = m.getActivities();
                        for (Activity a : activities) {
                            if (a.getName().equalsIgnoreCase("Spotify")) {
                                RichPresence rp = a.asRichPresence();
                                if (rp != null) {
                                    if (rp.getState() != null) {
                                        if (rp.getState().contains("Bilal")) {
                                            if (!titre[i].equalsIgnoreCase(rp.getDetails())) {
                                                channel.sendMessage(finalBilal.getAsMention() + " ! " + m.getUser().getAsMention() + " écoute " + rp.getDetails() + " de " + rp.getState()).queue();
                                            }
                                        }
                                        titre[i] = rp.getDetails();
                                    }
                                }
                            }
                        }
                    }
                }
            }, 0, 500);
        }
    }
}
