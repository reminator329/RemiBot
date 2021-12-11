package reminator.RemiBot.Commands.n;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.List;

public class NUpdateCommand implements Command {

    private final long COOLDOWN = 5 * 60 * 1000;
    private long lastCall = System.currentTimeMillis();

    @Override
    public Category getCategory() {
        return Category.N;
    }

    @Override
    public String getLabel() {
        return "nsfw-update";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"n-u", "nsfw-u", "nu"};
    }

    @Override
    public String getDescription() {
        return "Met à jour le contenu NSFW.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        NManager nsfw = NManager.get();

        long now = System.currentTimeMillis();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(author.getName(), author.getAvatarUrl());

        if(now-lastCall < COOLDOWN) {
            long remaining = COOLDOWN - (now - lastCall);
            long minutes = remaining / 60000;
            long seconds = (remaining-minutes*60000)/1000;
            String msg = "";
            if(minutes > 0) {
                msg += minutes + " minute";
                if(minutes > 1) {
                    msg += "s";
                }
                msg += " et ";
            }
            msg += seconds + " seconde";
            if(seconds > 1) {
                msg += "s";
            }
            embed.setColor(Color.RED);
            embed.appendDescription("**Erreur :** Veuillez attendre encore "+msg+" avant la prochaine update.");
            EnvoiMessage.sendMessage(event, embed.build());
            return;
        }

        lastCall = now;

        int categoriesAmount = nsfw.getCategories().size();
        int imagesAmount = nsfw.getImagesAmount();

        nsfw.updateCategories();
        nsfw.updateImages();

        int addedCategories = nsfw.getCategories().size()-categoriesAmount;
        int addedImages = nsfw.getImagesAmount()-imagesAmount;

        String msg = "Le contenu a bien été mis à jour :\n";
        msg += "+" + addedCategories+" catégorie"+(addedCategories > 1 ? "s" : "")+".\n";
        msg += "+" + addedImages +" image"+(addedImages > 1 ? "s" : "")+".\n";

        embed.setColor(Color.GREEN);
        embed.appendDescription(msg);
        EnvoiMessage.sendMessage(event, embed.build());
    }
}
