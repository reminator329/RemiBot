package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EloryaBilal extends Command {

    private static boolean execute = false;
    Timer timer;

    public EloryaBilal() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("elorya");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande Elorya");
        builder.appendDescription("Dès que la commande est exécuté, un ping sera fait aux Bilal quand Elorya écoutera du Bilal.");
        builder.addField("Signature", "`r!elorya`", false);
        return builder.build();
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
            channel.sendTyping().queue();
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
                                        if (rp.getState().equalsIgnoreCase("Bilal Hassani")) {
                                            if (!titre[i].equalsIgnoreCase(rp.getDetails())) {
                                                channel.sendTyping().queue();
                                                event.getChannel().sendMessage(finalBilal.getAsMention() + " ! " + m.getUser().getAsMention() + " écoute " + rp.getDetails() + " de " + rp.getState()).queue();
                                            }
                                        }
                                        titre[i] = rp.getDetails();
                                    }
                                }
                            }
                        }
                    }
                }
            }, 0, 1000);
        }
    }
}
