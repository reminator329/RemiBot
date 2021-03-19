package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;

public class NSFWUpdateCommand extends Command {

    private final long COOLDOWN = 5 * 60 * 1000;
    private long lastCall = System.currentTimeMillis();

    public NSFWUpdateCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("nsfw-update");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande d'update NSFW");
        builder.appendDescription("Met à jour le contenu NSFW.");
        builder.addField("Signature", "`r!nsfw-update`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();

        NSFWManager nsfw = NSFWManager.get();

        long now = System.currentTimeMillis();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(user.getName(), user.getAvatarUrl());

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
