package reminator.RemiBot.reactionpersonne;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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

        if (random.nextInt(100) == 14) {

            Message message = event.getMessage();

            User user = event.getAuthor();
            Emotes[] emotes = null;

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ELORYA.getId())) {
                emotes = new Emotes[]{Emotes.POULPE_CONTENT, Emotes.POULPE_PAS_CONTENT};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.REMINATOR.getId())) {
                emotes = new Emotes[]{Emotes.REMI, Emotes.POULPE_CONTENT, Emotes.SIDRA, Emotes.NATU, Emotes.DEOXYS, Emotes.chaudetIsWatchingU, Emotes.UPSSITECHED};

            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ALPHATASH.getId())) {
                emotes = new Emotes[]{Emotes.PENIS_CHAN, Emotes.PENIS, Emotes.CUM, Emotes.FAP, Emotes.PUSSY, Emotes.BJ, Emotes.UPSSITECHED, Emotes.THIBAULT};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.REDECO.getId())) {
                emotes = new Emotes[]{Emotes.BASTIEN, Emotes.UPSSITECHED, Emotes.HORNY};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.YAEL.getId())) {
                emotes = new Emotes[]{Emotes.POULPE_CONTENT, Emotes.POULPE_PAS_CONTENT};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.DORIAN.getId())) {
                emotes = new Emotes[]{Emotes.DORIAN, Emotes.UPSSITECHED};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ERAZZED.getId())) {
                emotes = new Emotes[]{Emotes.KILLIAN, Emotes.UPSSITECHED};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.HARPIERAPACE.getId())) {
                emotes = new Emotes[]{Emotes.TANGUY, Emotes.UPSSITECHED};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.DREAMPLUME.getId())) {
                emotes = new Emotes[]{Emotes.MATEO, Emotes.UPSSITECHED};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.SWAPHOLY.getId())) {
                emotes = new Emotes[]{Emotes.THOMAS, Emotes.UPSSITECHED};
            }

            if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.FEAVY.getId())) {
                emotes = new Emotes[]{Emotes.OUI};
            }
            if (emotes == null) return;

            int r = random.nextInt(emotes.length);
            Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
            assert emote != null;
            message.addReaction(emote).queue();
        }
    }
}
