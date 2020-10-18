package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Categories.BilalCategorie;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlbumCommand extends Command {

    // year : année - 1900 ; mouth : Calendar.MOUTH ; date/h/m : date - 1H
    private static final Date prochainAlbum = new Date(2020, Calendar.NOVEMBER, 6);

    public AlbumCommand() {
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

        Date date = new Date();
        String message;
        if (date.compareTo(prochainAlbum) < 0) {
            message = new SimpleDateFormat("d 'jours', h 'heures', m 'minutes', s 'seconds', S 'millisecondes'")
                    .format(new Date(prochainAlbum.getTime()-date.getTime() - 2000*3600));

        } else {
            message = "Pas de date pour le moment :/";
        }
        channel.sendMessage(message).queue();
    }
}
