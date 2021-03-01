package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

public class NSFWCommand extends Command {

    public NSFWCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("nsfw");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande NSFW");
        builder.appendDescription("Affichage une image NSFW aléatoire.");
        builder.addField("Signature", "`r!nsfw [categorie]`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        String[] args = event.getMessage().getContentRaw().split(" ");


        EmbedBuilder embed = new EmbedBuilder();
        NSFWCategory category = null;
        try {
            category = args.length == 1 ? NSFWCategory.random() : NSFWCategory.fromString(args[1]).orElseThrow(() -> new RuntimeException("Le catégorie `"+args[1]+"` n'existe pas."));
            File image = category.getImageManager().getRandomImage();
            InputStream inputStream = new FileInputStream(image);
            embed.setImage("attachment://nsfw.png")
                    .setDescription(category.label()+" :smirk:");
            embed.setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());
            channel.sendFile(inputStream, "nsfw.png").embed(embed.build()).queue();
        } catch (IllegalArgumentException e) {
            embed.setColor(Color.RED);
            embed.setTitle("RémiNSFW");
            embed.appendDescription("**Erreur :** Il n'y a aucune image dans la catégorie `"+category.label()+"`.");
            channel.sendMessage(embed.build()).queue();
        }catch (RuntimeException e) {
            embed.setColor(Color.RED);
            embed.setTitle("RémiNSFW");
            embed.appendDescription("**Erreur :** "+e.getMessage()+".");
            channel.sendMessage(embed.build()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
