package reminator.RemiBot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;
import java.util.List;

public class AmongusCommand implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    Timer timer;

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "ecoute-among-us";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"e-a-u", "eau", "ea", "ecoute-au", "ecoute-a-u", "ecoute-among", "ecoute-us"};
    }

    @Override
    public String getDescription() {
        return "Permet de notifier tout le monde lorsque quelqu'un joue à Among Us.\n\nQuand la commande est exécuté, elle active ou désactive l'envoi des messages.\nLes messages seront envoyés dans le salon où la commande a été exécutée.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();

        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        if (ecoutes.contains(channel)) {
            ecoutes.remove(channel);
            EnvoiMessage.sendMessage(event, "Arrêt de la commande");
            timer.cancel();
            timer.purge();
        } else {
            EnvoiMessage.sendMessage(event, "Début de la commande");
            ecoutes.add(channel);
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
