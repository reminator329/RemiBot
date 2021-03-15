package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;

public class NSFWCategoriesCommand extends Command {
    public NSFWCategoriesCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("nsfw-categories");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Liste les catégories NSFW");
        builder.appendDescription("Affiche la liste des catégories NSFW et le nombre d'images qu'elles contiennent.");
        builder.addField("Signature", "`r!nsfw-categories`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        NSFWManager nsfw = NSFWManager.get();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());

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
        channel.sendMessage(embed.build()).queue();
    }
}
