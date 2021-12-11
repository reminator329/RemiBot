package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.utils.EnvoiMessage;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PollCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "poll";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Créaction d'un sondage";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <question>[[ <réponse> <emoji>]*] [channel]";
    }

    @Override
    public MessageEmbed.Field[] getExtraFields() {
        return new MessageEmbed.Field[]{
                new MessageEmbed.Field("Exemple oui ou non", "r!poll \"Voulez-vous jouer ?\"", false),
                new MessageEmbed.Field("Exemple choix multiple", "r!poll \"Quel est votre animal préféré ?\" \"chat\" :cat: \"cheval\" :horse: \"souris\" :mouse:", false),
        };
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        if (args.size() > 1 && args.get(1).equalsIgnoreCase("stop")) {
            return;
        }

        User user = event.getAuthor();

        long channelId = channel.getIdLong();
        long authorId = user.getIdLong();
        ArrayList<String> reponses = new ArrayList<>();
        ArrayList<String> emojis = new ArrayList<>();
        final String[] question = {null};

        EmbedBuilder embedBuilder_base_base = new EmbedBuilder();
        embedBuilder_base_base.setColor(Color.PINK);
        embedBuilder_base_base.setFooter(user.getName(), user.getAvatarUrl());

        EmbedBuilder embedBuilder_base = new EmbedBuilder(embedBuilder_base_base);
        embedBuilder_base.setTitle("Création d'un sondage");

        EmbedBuilder embedBuilder_choix_question = new EmbedBuilder(embedBuilder_base);
        embedBuilder_choix_question.addField("Pose une question", "", false);
        EnvoiMessage.sendMessage(event, embedBuilder_choix_question.build());

        event.getJDA().addEventListener(new ListenerAdapter() {
            private long idMessage;

            private EmbedBuilder embedBuilder;
            private boolean fin = false;

            private void initEmbedBuilder() {
                this.embedBuilder = new EmbedBuilder(embedBuilder_base);

            }

            @Override
            public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getMember().getUser().getIdLong() != authorId) return;
                if (event.getMessageIdLong() != this.idMessage) return;

                initEmbedBuilder();

                String msg;

                if (!event.getReactionEmote().isEmoji()) {
                    msg = "<:" + event.getReactionEmote().getAsReactionCode() + ">";
                } else {
                    msg = event.getReactionEmote().getAsReactionCode();
                }

                emojis.add(msg);

                MessageEmbed.Field fieldSuite;
                fieldSuite = new MessageEmbed.Field("Choisis une réponse (`r!stop` pour arrêter)", "", false);


                envoiEmbed(fieldSuite);
            }

            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;
                if (reponses.size() != emojis.size()) return;

                String msg = event.getMessage().getContentRaw();
                if (msg.equalsIgnoreCase("r!stop")) {

                    if (emojis.size() > 0) {
                        if (!fin) {
                            channel.sendMessage("Dans quel channel envoyer le sondage ? `r!stop` pour annuler le sondage").queue();
                            fin = true;
                            return;
                        }
                    }
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if (fin) {
                    try {
                        TextChannel channel2 = event.getGuild().getTextChannelById(msg.replaceAll("[<#>]", ""));
                        afficheSondage(channel2);
                        event.getJDA().removeEventListener(this);
                    } catch (NumberFormatException | NullPointerException e) {
                        channel.sendMessage("Ceci n'est pas un channel tapez #<nom du cannel> pour le trouver.").queue();
                    }
                    return;
                }


                if (event.getMessage().getContentRaw().equalsIgnoreCase("r!poll")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }
                this.idMessage = event.getMessage().getIdLong();

                initEmbedBuilder();

                if (question[0] == null) {
                    question[0] = msg;
                    embedBuilder.addField(question[0], "", false);
                    embedBuilder.addField("Choisis une réponse (`r!stop` pour arrêter)", "", false);
                    channel.sendMessage(embedBuilder.build()).queue();
                    return;
                }

                if (reponses.size() == emojis.size()) {
                    reponses.add(msg);
                    channel.sendMessage(":arrow_up: Réagis à ta réponse pour choisir l'émoji associé.").queue();
                }
            }

            private void afficheSondage(TextChannel channel2) {
                EmbedBuilder sondage = new EmbedBuilder(embedBuilder_base_base);
                sondage.setTitle(question[0]);

                StringBuilder reponses_field = new StringBuilder();
                for (int i = 0; i < emojis.size(); i++) {
                    reponses_field.append(emojis.get(i)).append(" ").append(reponses.get(i)).append("\n\n");
                }
                sondage.addField("", reponses_field.toString(), false);
                sondage.addField("", "", false);
                sondage.addField("Clique sur les réactions en dessous pour voter !", "", false);

                channel2.sendMessage(sondage.build()).queue(reactPoll -> {
                    for (int i = 0; i < 20 && i < emojis.size(); i++) {
                        String emoji = emojis.get(i);
                        if (emoji.startsWith("<:")) {
                            reactPoll.addReaction(emoji.substring(2, emoji.length() - 1)).queue();
                        } else {
                            reactPoll.addReaction(emoji).queue();
                        }
                    }
                });
                for (int i = 20; i < emojis.size(); i += 20) {
                    int finalI = i;
                    channel2.sendMessage(".").queue(reactPoll -> {
                        for (int j = finalI; j < finalI + 20 && j < emojis.size(); j++) {
                            String emoji = emojis.get(j);
                            if (emoji.startsWith("<:")) {
                                reactPoll.addReaction(emoji.substring(2, emoji.length() - 1)).queue();
                            } else {
                                reactPoll.addReaction(emoji).queue();
                            }
                        }
                    });
                }
            }


            private void envoiEmbed(MessageEmbed.Field fieldSuite) {
                StringBuilder reponses_field = new StringBuilder();
                for (int i = 0; i < emojis.size(); i++) {
                    reponses_field.append(emojis.get(i)).append(" ").append(reponses.get(i)).append("\n\n");
                }
                embedBuilder.addField(question[0], reponses_field.toString(), false);
                embedBuilder.addField(fieldSuite);

                channel.sendMessage(embedBuilder.build()).queue();
            }
        });
    }
}
