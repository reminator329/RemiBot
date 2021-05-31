package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NCategoriesCommand implements Command {

    @Override
    public reminator.RemiBot.Categories.enums.Category getCategory() {
        return reminator.RemiBot.Categories.enums.Category.N;
    }

    @Override
    public String getLabel() {
        return "nsfw-categories";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"n-c", "nc", "nsfw-c"};
    }

    @Override
    public String getDescription() {
        return "Affiche la liste des catégories NSFW et le nombre d'images qu'elles contiennent.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        NManager nsfw = NManager.get();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(author.getName(), author.getAvatarUrl());

        Collection<Category> allCategories = nsfw.getCategories().values();
        String boyCategories = allCategories.stream().filter(cat -> cat.isBoy() && !cat.getId().equals("boy")).sorted()
                .map(cat -> String.format("**%s** (%d)", cat.getId(), cat.getImagesAmount()))
                .collect(Collectors.joining(", "));

        String girlCategories = allCategories.stream().filter(cat -> !cat.isBoy() && !cat.getId().equals("girl")).sorted()
                .map(cat -> String.format("**%s** (%d)", cat.getId(), cat.getImagesAmount()))
                .collect(Collectors.joining(", "));

        String msg = "**BOY** ("+nsfw.getBoyImagesAmount()+")\n\n";
        msg += boyCategories;
        msg += "\n\n";
        msg += "**GIRL** ("+nsfw.getGirlImagesAmount()+")\n\n";
        msg += girlCategories;

        msg += "\n\n**TOTAL :** " + nsfw.getImagesAmount() + " images uniques.";

        embed.setColor(Color.PINK);
        embed.appendDescription(msg);
        EnvoiMessage.sendMessage(event, embed.build());
    }
}
