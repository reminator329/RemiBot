package reminator.RemiBot.commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.Devoir.Model.BDDevoir;
import reminator.RemiBot.commands.Devoir.Model.BDDevoirJson;
import reminator.RemiBot.commands.Devoir.Model.Devoir;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DevoirCommand implements Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    @Override
    public Category getCategory() {
        return Category.DEVOIR;
    }

    @Override
    public String getLabel() {
        return "devoir";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"d"};
    }

    @Override
    public String getDescription() {
        return "Affiche les devoirs à faire";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        ArrayList<Devoir> devoirs = bdDevoir.getDevoirs(event.getAuthor());
        if (devoirs.isEmpty()) {
            new EnvoiMessage().sendMessage(event, "Vous n'avez pas de devoirs à faire");
            return;
        }
        User user = event.getAuthor();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle(user.getName());
        embedBuilder.appendDescription("Voici la liste de vos devoirs");

        ArrayList<Devoir> devoirsAll = new ArrayList<>();
        ArrayList<Devoir> devoirsPerso = new ArrayList<>();

        for (Devoir d : devoirs) {
            if (d.isAll()) {
                devoirsAll.add(d);
            } else {
                devoirsPerso.add(d);
            }
        }

        int numeroDevoir = 1;
        if (!devoirsAll.isEmpty()) {
            StringBuilder message = new StringBuilder();
            numeroDevoir = ajoutDevoirs(message, devoirsAll, numeroDevoir);
            embedBuilder.addField("Devoirs généraux", message.toString(), false);
        }
        if (!devoirsPerso.isEmpty()) {
            StringBuilder message = new StringBuilder();
            numeroDevoir = ajoutDevoirs(message, devoirsPerso, numeroDevoir);
            embedBuilder.addField("Devoirs perso", message.toString(), false);
        }

        embedBuilder.setFooter(user.getName() + " Il vous reste " + (numeroDevoir-1) + " devoir(s) à faire.", user.getAvatarUrl());
        new EnvoiMessage().sendMessage(event, embedBuilder.build());
    }

    private int ajoutDevoirs(StringBuilder message, ArrayList<Devoir> devoirs, int numeroDevoir) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM");
        for (Devoir d : devoirs) {
            message.append("[").append(numeroDevoir).append("] **").append(d.getCourse());
            if (d.getDate() != null) {
                message.append(" (").append(dateFormat.format(d.getDate())).append(") ");
            }
            message.append("** : ").append(d.getDescription()).append("\n");
            numeroDevoir++;
        }
        return numeroDevoir;
    }
}
