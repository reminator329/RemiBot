package reminator.RemiBot.Services.reactionpersonne;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Random;

public class ReactionPersonneService extends ListenerAdapter {

    private Random random = new Random();

    private JDA api;

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
            message.addReaction("\uD83D\uDC1F").queue();
        }


        if (random.nextInt(100) == 14) {

            Emotes[] emotes = null;

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.ELORYA.getId())) {
                emotes = new Emotes[]{Emotes.POULPE_CONTENT, Emotes.POULPE_PAS_CONTENT, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.REMINATOR.getId())) {
                emotes = new Emotes[]{Emotes.REMI, Emotes.POULPE_CONTENT, Emotes.SIDRA, Emotes.NATU, Emotes.DEOXYS, Emotes.chaudetIsWatchingU, Emotes.UPSSITECHED, Emotes.FOU};

            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.ALPHATASH.getId())) {
                emotes = new Emotes[]{Emotes.PENIS_CHAN, Emotes.PENIS, Emotes.CUM, Emotes.FAP, Emotes.PUSSY, Emotes.BJ, Emotes.UPSSITECHED, Emotes.THIBAULT, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.REDECO.getId())) {
                emotes = new Emotes[]{Emotes.BASTIEN, Emotes.UPSSITECHED, Emotes.HORNY, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.YAEL.getId())) {
                emotes = new Emotes[]{Emotes.POULPE_CONTENT, Emotes.POULPE_PAS_CONTENT, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.DORIAN.getId())) {
                emotes = new Emotes[]{Emotes.DORIAN, Emotes.UPSSITECHED, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.ERAZZED.getId())) {
                emotes = new Emotes[]{Emotes.KILLIAN, Emotes.UPSSITECHED, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.HARPIERAPACE.getId())) {
                emotes = new Emotes[]{Emotes.TANGUY, Emotes.UPSSITECHED, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.DREAMPLUME.getId())) {
                emotes = new Emotes[]{Emotes.MATEO, Emotes.UPSSITECHED, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.SWAPHOLY.getId())) {
                emotes = new Emotes[]{Emotes.THOMAS, Emotes.UPSSITECHED, Emotes.FOU};
            }

            if (user.getId().equals(reminator.RemiBot.Services.reactionpersonne.User.FEAVY.getId())) {
                emotes = new Emotes[]{Emotes.OUI, Emotes.FOU};
            }
            if (emotes == null) return;

            int r = random.nextInt(emotes.length);
            Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
            assert emote != null;
            message.addReaction(emote).queue();
        }

    }
}
