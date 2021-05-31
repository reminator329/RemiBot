package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;

public class NSFWCommand extends Command {

    public NSFWCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("nsfw");
        this.setHelp(setHelp());

        // Init
        NSFWManager.get();
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande NSFW");
        builder.appendDescription("Affichage une image NSFW aléatoire.");
        builder.addField("Signature", "`r!nsfw (categorie)`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            TextChannel channelTest = (TextChannel) event.getChannel();

            if (!channelTest.isNSFW()) {
                //EnvoiMessage.sendMessage(event, "Ce n'est pas un channel NSFW !!");
                //return;
            }
        }

        User user = event.getAuthor();

        NSFWManager nsfw = NSFWManager.get();

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        String imageURL;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(user.getName(), user.getAvatarUrl());

        if (args.length > 1) {
            Category category = nsfw.getCategoryById(args[1].toLowerCase());

            if (category == null) {
                embed.setColor(Color.RED);
                embed.appendDescription("La catégorie `" + args[1].toLowerCase() + "` n'existe pas.");
                EnvoiMessage.sendMessage(event, embed.build());
                return;
            }

            imageURL = nsfw.getRandomImageURL(category);

            if (imageURL == null) {
                embed.setColor(Color.RED);
                embed.appendDescription("Aucune image trouvée dans la catégorie `" + category.getTitle() + "`.");
                EnvoiMessage.sendMessage(event, embed.build());
                return;
            }

            embed.setDescription(category.getTitle() + " :smirk:");
        }else{
            imageURL = nsfw.getRandomImageURL();
        }

        embed.setColor(Color.PINK)
                .setImage(imageURL);

        EnvoiMessage.sendMessage(event, embed.build());
    }
}
