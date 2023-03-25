package reminator.RemiBot.Services.reactionpersonne;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReactionPersonneService extends ListenerAdapter {

    private final Random random = new Random();

    private final JDA api;

    public ReactionPersonneService(JDA api) {
        this.api = api;
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //api.getEmoteCache().forEach(System.out::println);


        User user = event.getAuthor();

        Message message = event.getMessage();

        int jour = new Date().getDate();
        int mois = new Date().getMonth();

        if (jour == 1 && mois == 3) {
            message.addReaction(Emoji.fromUnicode("\uD83D\uDC1F")).queue();
        }


        if (random.nextInt(100) == 14) {

            ArrayList<Emotes> emotes = new ArrayList<>();
            emotes.add(Emotes.POULPE_CONTENT);
            emotes.add(Emotes.POULPE_PAS_CONTENT);
            emotes.add(Emotes.FOU);
            emotes.add(Emotes.OUI);
            emotes.add(Emotes.NON);
            emotes.add(Emotes.YOUPI);
            emotes.add(Emotes.YOUPICAT);

            if (user.getId().equals(Users.ELORYA.getId())) {

            }

            if (user.getId().equals(Users.REMINATOR.getId())) {
                emotes.addAll(List.of(Emotes.REMI, Emotes.SIDRA, Emotes.NATU, Emotes.DEOXYS, Emotes.chaudetIsWatchingU, Emotes.UPSSITECHED));

            }

            if (user.getId().equals(Users.ALPHATASH.getId())) {
                emotes.addAll(List.of(Emotes.PENIS_CHAN, Emotes.PENIS, Emotes.CUM, Emotes.FAP, Emotes.PUSSY, Emotes.BJ, Emotes.UPSSITECHED, Emotes.THIBAULT));
            }

            if (user.getId().equals(Users.REDECO.getId())) {
                emotes.addAll(List.of(Emotes.BASTIEN, Emotes.UPSSITECHED, Emotes.HORNY));
            }

            if (user.getId().equals(Users.YAEL.getId())) {

            }

            if (user.getId().equals(Users.DORIAN.getId())) {
                emotes.addAll(List.of(Emotes.DORIAN, Emotes.UPSSITECHED));
            }

            if (user.getId().equals(Users.ERAZZED.getId())) {
                emotes.addAll(List.of(Emotes.KILLIAN, Emotes.UPSSITECHED));
            }

            if (user.getId().equals(Users.HARPIERAPACE.getId())) {
                emotes.addAll(List.of(Emotes.TANGUY, Emotes.UPSSITECHED));
            }

            if (user.getId().equals(Users.DREAMPLUME.getId())) {
                emotes.addAll(List.of(Emotes.MATEO, Emotes.UPSSITECHED));
            }

            if (user.getId().equals(Users.SWAPHOLY.getId())) {
                emotes.addAll(List.of(Emotes.THOMAS, Emotes.UPSSITECHED));
            }

            if (user.getId().equals(Users.FEAVY.getId())) {

            }

            int r = random.nextInt(emotes.size());
            Emoji emote = api.getEmojiCache().getElementById(emotes.get(r).getId());
            assert emote != null;
            message.addReaction(emote).queue();
        }

    }
}
