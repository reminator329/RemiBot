package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PingCommand extends Command {

    public PingCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ping");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ping");
        builder.appendDescription("Répond pong !");
        builder.addField("Signature", "`r!ping`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Pong !");
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
    }
}
