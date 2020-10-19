package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.edt.Edt;
import sun.java2d.pipe.SpanShapeRenderer;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProchainCoursCommand extends Command {

    public ProchainCoursCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("prochain-cours");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        return null;
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("'Le 'dd/MM' à 'HH:mm");

        Edt edt = new Edt();
        JSONObject cours = edt.getNextCourse();
        String[] typeCours = edt.getTypeCours(cours);


        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Prochain cours");
        builder.appendDescription(cours.getString("summary"));


        try {
            builder.addField("Date", dateFormat2.format(new Date(dateFormat1.parse(cours.getJSONObject("start").getString("dateTime")).getTime())), false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        builder.addField("Type", typeCours[0], false);
        if (typeCours[1] != null && typeCours[1] != "") {
            if (typeCours[1].contains("discord")) {
                builder.addField("Discord", typeCours[1], false);
            } else {
                builder.addField("Zoom", typeCours[1], false);
            }
        }
        channel.sendMessage(builder.build()).queue();
    }
}
