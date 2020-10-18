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
        JSONObject o = edt.getNextCourse();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Prochain cours");
        builder.appendDescription(o.getString("summary"));
        try {
            builder.addField("Date", dateFormat2.format(dateFormat1.parse(o.getJSONObject("start").getString("dateTime"))), false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        channel.sendMessage(builder.build()).queue();
    }
}
