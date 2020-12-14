package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;

public class PongCommand extends Command {

    public PongCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("pong");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande pong");
        builder.appendDescription("Hmm");
        builder.addField("Signature", "`r!pong`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Pong !");
        builder.setFooter(member.getNickname(), member.getUser().getAvatarUrl());

        channel.sendMessage(builder.build()).queue();
        channel.sendMessage("https://tenor.com/view/scary-gif-5252759").queue();

    }


}
