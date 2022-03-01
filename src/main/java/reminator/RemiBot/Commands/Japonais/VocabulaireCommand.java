package reminator.RemiBot.Commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.Japonais.model.BDVocabulaire;
import reminator.RemiBot.Commands.Japonais.model.Categorie;
import reminator.RemiBot.Commands.Japonais.model.Vocabulaire;
import reminator.RemiBot.Commands.Japonais.model.VocabulaireParserCSV;
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

        Set<Categorie> cats = args.stream().map(Categorie::new).collect(Collectors.toSet());
        BDVocabulaire bdVocabulaire = VocabulaireParserCSV.getInstance().update().generateBDVocabulaire(cats);
        if (bdVocabulaire.isEmpty()) {
            EnvoiMessage.sendMessage(event, ":warning: Aucun vocabulaire ne correspond aux catégories choisies.");
            return;
        }
        final Vocabulaire[] vocabulaire = {bdVocabulaire.getRandomVocabulary()};


        final EmbedBuilder[] embedBuilder = {new EmbedBuilder().setTitle("Le premier qui trouve 10 mots de vocabulaire gagne la partie !").setDescription("Que le meilleur gagne !")};
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());

        embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].getFr()).setDescription("Comment dit-on ce mot en japonais ?");
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());


        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getMessage().getContentRaw().length() == 0) return;

                String msg = event.getMessage().getContentRaw();
                User user = event.getAuthor();

                if(msg.startsWith("r!vocabulaire")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(vocabulaire[0].isCorrect(msg)) {
                    EnvoiMessage.sendMessage(event, "** Bravo " + user.getAsMention() + "** :partying_face:\n" + vocabulaire[0].getFr() + " se dit bien " + vocabulaire[0].getJaponais() + " (" + vocabulaire[0].getRoomaji() + ")");
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
                        vocabulaire[0] = bdVocabulaire.getRandomVocabulary();
                        embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].getFr()).setDescription("Comment dit-on ce mot en japonais ?");
                        EnvoiMessage.sendMessage(event, embedBuilder[0].build());
                    }
                    return;
                }

                if(msg.equalsIgnoreCase("jsp")) {
                    EnvoiMessage.sendMessage(event, "Dommage, " + vocabulaire[0].getFr() + " se dit " + vocabulaire[0].getJaponais() + " (" + vocabulaire[0].getRoomaji() + ")");
                    vocabulaire[0] = bdVocabulaire.getRandomVocabulary();
                    embedBuilder[0] = new EmbedBuilder().setTitle(vocabulaire[0].getFr()).setDescription("Comment dit-on ce mot en japonais ?");
                    EnvoiMessage.sendMessage(event, embedBuilder[0].build());
                }
            }
        });
    }
}
