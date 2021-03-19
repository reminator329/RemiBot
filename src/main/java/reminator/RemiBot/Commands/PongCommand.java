package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Model.Gif;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.List;
import java.util.Objects;

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
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel2 = event.getChannel();
        User user = event.getAuthor();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Ping !");
        builder.setFooter(user.getName(), user.getAvatarUrl());
        builder.setImage(user.getAvatarUrl());
        if (event.isFromGuild()) {
            Member member = event.getMember();

            if (member != null) {
                List<Activity> activities = member.getActivities();
                for (Activity a : activities) {
                    if (a.getName().equalsIgnoreCase("Spotify")) {
                        RichPresence rp = a.asRichPresence();
                        if (rp != null) {
                            try {
                                builder.setImage(Objects.requireNonNull(rp.getLargeImage()).getUrl());
                            } catch (NullPointerException ignored) {
                            }
                            System.out.println(rp.getDetails());
                            String message = member.getUser().getName() + " écoute " + rp.getDetails() + " de " + rp.getState();
                            builder.setFooter(message, member.getUser().getAvatarUrl());
                        }
                    }
                }
            }
        }

        EnvoiMessage.sendMessage(event, builder.build());
        EnvoiMessage.sendPrivate(event.getAuthor(), Gif.getRandom().getUrl());
    }
}
