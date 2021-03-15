package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;

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
    public void executerCommande(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if (!channel.isNSFW()) {
            channel.sendMessage("Ce n'est pas un channel NSFW !!").queue();
            return;
        }

        Member member = event.getMember();

        NSFWManager nsfw = NSFWManager.get();

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        String imageURL;

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());

        if (args.length > 1) {
            Category category = nsfw.getCategoryById(args[1].toLowerCase());

            if (category == null) {
                embed.setColor(Color.RED);
                embed.appendDescription("La catégorie `" + args[1].toLowerCase() + "` n'existe pas.");
                channel.sendMessage(embed.build()).queue();
                return;
            }

            imageURL = nsfw.getRandomImageURL(category);

            if (imageURL == null) {
                embed.setColor(Color.RED);
                embed.appendDescription("Aucune image trouvée dans la catégorie `" + category.getTitle() + "`.");
                channel.sendMessage(embed.build()).queue();
                return;
            }

            embed.setDescription(category.getTitle() + " :smirk:");
        }else{
            imageURL = nsfw.getRandomImageURL();
        }

        embed.setColor(Color.PINK)
                .setImage(imageURL);

        channel.sendMessage(embed.build()).queue();
    }
}
