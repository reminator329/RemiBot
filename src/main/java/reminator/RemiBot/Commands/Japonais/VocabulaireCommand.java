package reminator.RemiBot.Commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.Japonais.enums.Categorie;
import reminator.RemiBot.Commands.Japonais.enums.Katakana;
import reminator.RemiBot.Commands.Japonais.enums.Vocabulaire;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;
import java.util.stream.Collectors;

public class VocabulaireCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.JAPONAIS;
    }

    @Override
    public String getLabel() {
        return "vocabulaire";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Le premier qui trouve 10 mots de vocabulaire gagne la partie !";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + "[< categorie>*]";
    }

    @Override
    public MessageEmbed.Field[] getExtraFields() {
        return new MessageEmbed.Field[]{
                new MessageEmbed.Field("Exemple avec plusieurs catégories", "r!vocabulaire s7 position", false),
        };
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        long channelId = channel.getIdLong();
        Map<User, Integer> scores = new HashMap<>();
        System.out.println(args);
        Set<String> cats = args.size() == 0 ? null : new HashSet<>(args);


        final EmbedBuilder[] embedBuilder = {new EmbedBuilder().setTitle("Le premier qui trouve 10 mots de vocabulaire gagne la partie !").setDescription("Que le meilleur gagne !")};
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());
        final Vocabulaire[] vocabulaire = {Vocabulaire.getRandom(cats)};

        embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].fr()).setDescription("Comment dit-on ce mot en japonais ?");
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());


        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;
                if (event.getChannel().getIdLong() != channelId) return;

                String msg = event.getMessage().getContentRaw();
                User user = event.getAuthor();

                if(vocabulaire[0].equals(msg)) {
                    EnvoiMessage.sendMessage(event, "** Bravo " + user.getAsMention() + "** :partying_face:\n" + vocabulaire[0].fr() + " se dit bien " + vocabulaire[0].japonais());
                    int score;
                    if (scores.containsKey(user)) {
                        score = scores.get(user) + 1;
                        scores.replace(user, score);
                    } else {
                        score = 1;
                        scores.put(user, score);
                    }
                    EnvoiMessage.sendMessage(event, user.getAsMention() + " a maintenant " + score + " points !");
                    if (score == 10) {
                        EnvoiMessage.sendMessage(event, user.getAsMention() + " a gagné !!! :partying_face: :partying_face: :partying_face:");
                        EmbedBuilder classement = new EmbedBuilder().setTitle("Classement des joueurs de la partie");
                        StringBuilder s = new StringBuilder();
                        for(Map.Entry<User, Integer> e : scores.entrySet()) {
                            s.append(e.getKey().getAsMention()).append(" : ").append(e.getValue()).append("\n");
                        }
                        classement.addField("Test", s.toString(), false);
                        EnvoiMessage.sendMessage(event, classement.build());
                        event.getJDA().removeEventListener(this);
                    } else {
                        vocabulaire[0] = Vocabulaire.getRandom(cats);
                        embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].fr()).setDescription("Comment dit-on ce mot en japonais ?");
                        EnvoiMessage.sendMessage(event, embedBuilder[0].build());
                    }
                    return;
                }

                if(msg.equalsIgnoreCase("r!vocabulaire")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(msg.equalsIgnoreCase("jsp")) {
                    EnvoiMessage.sendMessage(event, "Dommaage, " + vocabulaire[0].fr() + " se dit " + vocabulaire[0].japonais());
                    vocabulaire[0] = Vocabulaire.getRandom(cats);
                    embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].fr()).setDescription("Comment dit-on ce mot en japonais ?");
                    EnvoiMessage.sendMessage(event, embedBuilder[0].build());
                }
            }
        });
    }
}
