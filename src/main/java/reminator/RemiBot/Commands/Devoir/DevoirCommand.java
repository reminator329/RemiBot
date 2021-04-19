package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.Model.Devoir;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DevoirCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir");
        this.addAlias("d");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devoir");
        builder.appendDescription("Affiche les devoirs à faire");
        builder.addField("Signature", "`r!devoir`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        ArrayList<Devoir> devoirs = bdDevoir.getDevoirs(event.getAuthor());
        if (devoirs.isEmpty()) {
            EnvoiMessage.sendMessage(event, "Vous n'avez pas de devoirs à faire");
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
        EnvoiMessage.sendMessage(event, embedBuilder.build());
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
