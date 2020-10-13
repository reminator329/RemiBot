package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.RandomImagePicker;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BilalCommand extends Command {
    private RandomImagePicker randomImagePicker;

    {
        try {
            randomImagePicker = new RandomImagePicker("/images");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public BilalCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("bilal");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande bilal");
        builder.appendDescription("Affiche une super photo de Bilal tirée au hasard :heart:");

        builder.addField("Signature", "`r!bilal`", false);

        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        EmbedBuilder embed = new EmbedBuilder();
        InputStream file = null;
        try {
            file = new FileInputStream(randomImagePicker.getRandomImage());
            embed.setImage("attachment://bilal.png") // we specify this in sendFile as "cat.png"
                    .setDescription("BILAAAL :heart:");
            channel.sendFile(file, "bilal.png").embed(embed.build()).queue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
