package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.music.Song;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class PlusMoinsCommand extends Command {

    public PlusMoinsCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("plus-ou-moins");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande plus-ou-moins");
        builder.appendDescription("Trouve le nombre entre 0 et 1 000 000 000");

        builder.addField("Signature", "`r!plus-ou-moins`", false);

        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();
        long channelId = channel.getIdLong();
        long authorId = user.getIdLong();

        final int[] tryAmount = {1};
        int nombre = (int) (Math.random() * 1000000000);
        String commande = this.getPrefix() + this.getLabel();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Trouve le nombre entre 0 et 1 000 000 000\nÉcrit 'stop' si tu abandonnes.");
        embedBuilder.setFooter(user.getName(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();

        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;

                String msg = event.getMessage().getContentRaw();

                if (msg.equalsIgnoreCase(commande)) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setFooter(user.getName(), user.getAvatarUrl());
                if (msg.equalsIgnoreCase("stop")) {
                    builder.setTitle("" + nombre);
                    builder.appendDescription("C'était le nombre à trouver !");
                    channel.sendMessage(builder.build()).queue();
                    event.getJDA().removeEventListener(this);
                    return;
                }

                try {
                    int choix = Integer.parseInt(msg);

                    if (choix == nombre) {
                        builder.setTitle("Gagné !");
                        builder.appendDescription("C'était bien " + nombre);
                        builder.addField("Nombre de coups", "" + tryAmount[0], false);
                        channel.sendMessage(builder.build()).queue();
                        event.getJDA().removeEventListener(this);
                        return;
                    }

                    builder.setTitle("" + choix);
                    if (choix < nombre) {
                        builder.appendDescription("C'est plus !");
                    } else {
                        builder.appendDescription("C'est moins !");
                    }
                    channel.sendMessage(builder.build()).queue();


                    tryAmount[0]++;

                } catch (NumberFormatException e){
                    return;
                }
            }
        });
    }
}
