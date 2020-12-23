package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.time.Duration;
import java.util.List;

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
        MessageChannel channel2 = event.getChannel();
        Member member = event.getMember();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Ping !");
        builder.setFooter(member.getNickname(), member.getUser().getAvatarUrl());
        builder.setImage(member.getUser.getLargeImage.getUrl());

        if (member != null) {
            List<Activity> activities = member.getActivities();
            for (Activity a : activities) {
                if (a.getName().equalsIgnoreCase("Spotify")) {
                    RichPresence rp = a.asRichPresence();
                    if (rp != null) {
                        try {
                            builder.setImage(rp.getLargeImage().getUrl());
                        } catch (NullPointerException ignored) {}
                        System.out.println(rp.getDetails());
                        String message = member.getUser().getName() + " écoute " + rp.getDetails() + " de " + rp.getState();
                        builder.setFooter(message, member.getUser().getAvatarUrl());
                    }
                }
            }
        }

        channel2.sendMessage(builder.build()).queue();


        event.getAuthor().openPrivateChannel()
                .flatMap(channel -> channel.sendMessage("https://tenor.com/view/scary-gif-5252759"))
                .queue();

    }


}
