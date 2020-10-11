package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class Album extends Command {

    // year : année - 1900 ; mouth : Calendar.MOUTH ; date/h/m : date - 1H
    private static final Date prochainAlbum = new Date(2020-1900, Calendar.NOVEMBER, 5, 23, 0);

    public Album() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("album");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande album");
        builder.appendDescription("Donne le temps avant la sortie du prochain album de Bilal !");

        builder.addField("Signature", "`r!album`", false);

        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        channel.sendTyping().queue();

        Date date = new Date();

        String message;
        if (date.compareTo(prochainAlbum) < 0) {
            message = "Prochain album de Bilal dans :\n";
            boolean flag = false;
            long diff = prochainAlbum.getTime() - date.getTime();

            float jours = ((float)diff) /(1000*60*60*24);
            if (jours >= 2) {
                message += (int)jours + " jours";
                flag = true;
            } else if (jours >= 1) {
                message += (int)jours + " jour";
                flag = true;
            }
            float heures = jours - ((int) jours);
            heures *= 24;
            if (heures >= 2) {
                if (flag) {
                    message += ", ";
                }
                message += (int)heures + " heures";
                flag = true;
            } else if (heures >= 1){
                if (flag) {
                    message += ", ";
                }
                message += (int)heures + " heure";
                flag = true;
            }
            float minutes = heures - ((int) heures);
            minutes *= 60;
            if (minutes >= 2) {
                if (flag) {
                    message += ", ";
                }
                message += (int)minutes + " minutes";
                flag = true;
            } else if (minutes >= 1){
                if (flag) {
                    message += ", ";
                }
                message += (int)minutes + " minute";
                flag = true;
            }
            float secondes = minutes - ((int) minutes);
            secondes *= 60;
            if (secondes >= 2) {
                if (flag) {
                    message += ", ";
                }
                message += (int)secondes + " secondes";
                flag = true;
            } else if (secondes >= 1){
                if (flag) {
                    message += ", ";
                }
                message += (int)secondes + " secondes";
                flag = true;
            }
            float mili = secondes - ((int) secondes);
            mili *= 1000;
            if (mili >= 2) {
                if (flag) {
                    message += ", ";
                }
                message += (int)mili + " milisecondes";
            } else if (mili >= 1){
                if (flag) {
                    message += ", ";
                }
                message += (int)mili + " milisecondes";
            }
        } else {
            message = "Pas de date pour le moment :/";
        }

        channel.sendMessage(message).queue();
    }
}
