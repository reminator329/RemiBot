package reminator.RemiBot.Services.pricescanner.update;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public interface ProductUpdate {
    boolean hasChange();

    String getMessage();

    Color getColor();

    default MessageEmbed toEmbed() {
        return new EmbedBuilder()
                .setDescription(getMessage())
                .setColor(getColor())
                .build();
    }
}
