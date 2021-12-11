package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;
import java.util.List;

public class EcouteBilalCommand implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    Timer timer;

    @Override
    public Category getCategory() {
        return Category.BILAL;
    }

    @Override
    public String getLabel() {
        return "ecoute-bilal";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"e-b", "eb", "ecoute-b"};
    }

    @Override
    public String getDescription() {
        return "Permet de notifier les Bilal lorsque quelqu'un écoute une musique de Bilal Hassani.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        if (ecoutes.contains(channel)) {
            channel.sendMessage("Arrêt de la commande").queue();
            timer.cancel();
            timer.purge();
            ecoutes.remove(channel);
        } else {
            channel.sendMessage("Début de la commande").queue();
            ecoutes.add(channel);
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
