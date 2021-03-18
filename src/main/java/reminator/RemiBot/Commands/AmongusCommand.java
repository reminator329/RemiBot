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

public class AmongusCommand extends Command{

    private static boolean execute = false;
    Timer timer;

    public AmongusCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ecoute-among-us");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ecoute-among-us");
        builder.appendDescription("Permet de notifier tout le monde lorsque quelqu'un joue à Among Us.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.");
        builder.addField("Signature", "`r!ecoute-among-us`", false);
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
            execute = false;
            EnvoiMessage.sendMessage(event, "Arrêt de la commande");
            timer.cancel();
            timer.purge();
        } else {
            EnvoiMessage.sendMessage(event, "Début de la commande");
            execute = true;
            java.util.List<Member> members = event.getGuild().getMembers();
            java.util.List<Role> roles = event.getGuild().getRoles();


            Role everyone = null;
            for (Role r : roles) {
                if (r.getName().equalsIgnoreCase("@everyone")) {
                    everyone = r;
                }
            }

            timer = new Timer();
            final String[] jeu = new String[members.size()];
            Arrays.fill(jeu, "");

            Role finalEveryone = everyone;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (int i=0; i<members.size(); i++) {
                        Member m = members.get(i);
                        List<Activity> activities = m.getActivities();
                        boolean playsAmong = false;
                        for (Activity a : activities) {
                            String j = a.getName();
                            if (j.equalsIgnoreCase("Among Us")) {
                                if (!jeu[i].equalsIgnoreCase(j)) {
                                    EnvoiMessage.sendMessage(event, finalEveryone.getAsMention() + "\n" + m.getUser().getAsMention() + " joue a Among Us !!!");
                                }
                                playsAmong = true;
                                jeu[i] = j;
                            }
                        }
                        if (!playsAmong) {
                            jeu[i] = "";
                        }
                    }
                }
            }, 0, 1000);
        }
    }
}
