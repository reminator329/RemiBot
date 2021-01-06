package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdatePermissionsEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.bot.RemiBot;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;

public class PollCommand extends Command {

    public PollCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("poll");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande poll");
        builder.appendDescription("Créaction d'un sondage");
        builder.addField("Signature", "`r!poll <question>[[ <réponse> <emoji>]*] [channel]`", false);
        builder.addField("Exemple oui ou non", "r!poll \"Voulez-vous jouer ?\"", false);
        builder.addField("Exemple choix multiple", "r!poll \"Quel est votre animal préféré ?\" \"chat\" :cat: \"cheval\" :horse: \"souris\" :mouse:", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length > 1 && args[1].equalsIgnoreCase("stop")) {
            return;
        }

        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();

        long channelId = channel.getIdLong();
        long authorId = user.getIdLong();
        ArrayList<String> reponses = new ArrayList<>();
        ArrayList<String> emojis = new ArrayList<>();
        final String[] question = {null};

        EmbedBuilder embedBuilder_base = new EmbedBuilder();
        embedBuilder_base.setColor(Color.PINK);
        embedBuilder_base.setTitle("Création d'un sondage");
        embedBuilder_base.setFooter(user.getName(), user.getAvatarUrl());

        EmbedBuilder embedBuilder_choix_question = new EmbedBuilder(embedBuilder_base);
        embedBuilder_choix_question.addField("Pose une question", "", false);
        channel.sendMessage(embedBuilder_choix_question.build()).queue();

        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;
                String[] args = event.getMessage().getContentRaw().split("\\s+");
                if (args.length > 1 && args[1].equalsIgnoreCase("stop")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                String msg = event.getMessage().getContentRaw();

                EmbedBuilder embedBuilder = new EmbedBuilder(embedBuilder_base);
                if (question[0] == null) {
                    question[0] = msg;
                    embedBuilder.addField(question[0], "", false);
                    embedBuilder.addField("Choisis une réponse (`r!poll stop` pour arrêter)", "", false);
                    channel.sendMessage(embedBuilder.build()).queue();
                    return;

                }

                MessageEmbed.Field fieldSuite;
                if (reponses.size() == emojis.size()) {
                    reponses.add(msg);
                    fieldSuite = new MessageEmbed.Field("Choisis un émoji pour " + reponses.get(reponses.size() - 1), "", false);
                } else {
                    emojis.add(msg);
                    fieldSuite = new MessageEmbed.Field("Choisis une réponse (`r!poll stop` pour arrêter)", "", false);
                }

                String reponses_field = "";
                for (int i = 0; i < emojis.size(); i++) {
                    reponses_field += emojis.get(i) + " " + reponses.get(i) + "\n";
                }
                embedBuilder.addField(question[0], reponses_field, false);
                embedBuilder.addField(fieldSuite);

                channel.sendMessage(embedBuilder.build()).queue();
            }
        });
    }
}
