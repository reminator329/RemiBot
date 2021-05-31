package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.List;

public class NCommand implements Command {

    public NCommand() {
        // Init
        NManager.get();
    }

    @Override
    public reminator.RemiBot.Categories.enums.Category getCategory() {
        return reminator.RemiBot.Categories.enums.Category.N;
    }

    @Override
    public String getLabel() {
        return "nsfw";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Affichage une image NSFW aléatoire.";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " [categorie]";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        if (event.isFromGuild()) {
            TextChannel channelTest = (TextChannel) event.getChannel();

            if (!channelTest.isNSFW()) {
                EnvoiMessage.sendMessage(event, "Ce n'est pas un channel NSFW !!");
                return;
            }
        }

        User user = event.getAuthor();

        NManager nsfw = NManager.get();

        String imageURL;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(user.getName(), user.getAvatarUrl());

        if (args.size() > 1) {
            Category category = nsfw.getCategoryById(args.get(1).toLowerCase());

            if (category == null) {
                embed.setColor(Color.RED);
                embed.appendDescription("La catégorie `" + args.get(1).toLowerCase() + "` n'existe pas.");
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
        } else {
            imageURL = nsfw.getRandomImageURL();
        }

        embed.setColor(Color.PINK)
                .setImage(imageURL);

        EnvoiMessage.sendMessage(event, embed.build());
    }
}
