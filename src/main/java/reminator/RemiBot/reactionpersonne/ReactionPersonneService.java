package reminator.RemiBot.reactionpersonne;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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

        Message message = event.getMessage();

        User user = event.getAuthor();

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ELORYA.getId())) {
            Emote emote = api.getEmoteCache().getElementById(Emotes.NON.getId());
//            message.addReaction(emote).queue();
//            message.addReaction("U+1F44E").queue();
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.REMINATOR.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.REMI, Emotes.POULPE_CONTENT, Emotes.SIDRA, Emotes.NATU, Emotes.DEOXYS, Emotes.chaudetIsWatchingU, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ALPHATASH.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.PENIS_CHAN, Emotes.PENIS, Emotes.CUM, Emotes.FAP, Emotes.PUSSY, Emotes.BJ, Emotes.UPSSITECHED, Emotes.THIBAULT};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.REDECO.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.BASTIEN, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.YAEL.getId())) {
            Emote emote = api.getEmoteCache().getElementById(Emotes.POULPE_PAS_CONTENT.getId());
            if(random.nextInt(10) < 3) {
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.DORIAN.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.DORIAN, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.ERAZZED.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.KILLIAN, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.HARPIERAPACE.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.TANGUY, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.DREAMPLUME.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.MATEO, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.SWAPHOLY.getId())) {
            if(random.nextInt(10) < 3) {
                Emotes[] emotes = new Emotes[]{Emotes.THOMAS, Emotes.UPSSITECHED};
                int r = random.nextInt(emotes.length);
                Emote emote = api.getEmoteCache().getElementById(emotes[r].getId());
                assert emote != null;
                message.addReaction(emote).queue();
            }
        }

        if (user.getId().equals(reminator.RemiBot.reactionpersonne.User.FEAVY.getId())) {
            Emote emote = api.getEmoteCache().getElementById(Emotes.OUI.getId());
            if(random.nextInt(10) < 3) {
                message.addReaction(emote).queue();
            }
        }
    }
}
