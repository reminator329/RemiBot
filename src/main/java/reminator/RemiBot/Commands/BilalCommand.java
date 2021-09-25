package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;
import reminator.RemiBot.utils.RandomImagePicker;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class BilalCommand implements Command {

    private RandomImagePicker randomImagePicker;

    {
        try {
            randomImagePicker = new RandomImagePicker("images");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategory() {
        return Category.BILAL;
    }

    @Override
    public String getLabel() {
        return "bilal";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"b"};
    }

    @Override
    public String getDescription() {
        return "Affiche une super photo de Bilal tirée au hasard :heart:";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        /*
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
         */

        EmbedBuilder embed = new EmbedBuilder();
        InputStream file;
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
