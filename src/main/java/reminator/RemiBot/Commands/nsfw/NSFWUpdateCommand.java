package reminator.RemiBot.Commands.nsfw;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.bot.RemiBot;

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
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        NSFWManager nsfw = NSFWManager.get();

        long now = System.currentTimeMillis();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Rémi NSFW")
                .setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());

        if(now-lastCall < COOLDOWN) {
            embed.setColor(Color.RED);
            embed.appendDescription("**Erreur :** Veuillez attendre au moins 5 minutes entre deux updates.");
            channel.sendMessage(embed.build()).queue();
            return;
        }

        lastCall = now;

        nsfw.updateCategories();
        nsfw.updateImages();

        embed.setColor(Color.GREEN);
        embed.appendDescription("Le contenu a bien été mis à jour.");
        channel.sendMessage(embed.build()).queue();
    }
}
