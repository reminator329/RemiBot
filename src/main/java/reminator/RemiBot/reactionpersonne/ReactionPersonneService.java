package reminator.RemiBot.reactionpersonne;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReactionPersonneService extends ListenerAdapter {

    private JDA api;

    public ReactionPersonneService(JDA api) {
        this.api = api;
    }



    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Message message = event.getMessage();

        User user = event.getAuthor();

        // Elorya
        if (user.getId().equals("427256562596315167")) {
            Emote emote = api.getEmoteCache().getElementById(Emotes.NON.getId());
            message.addReaction(emote).queue();
            message.addReaction("U+1F44E").queue();
        }

        // Elorya
        if (user.getId().equals("264490592610942976")) {
            Emote emote = api.getEmoteCache().getElementById(Emotes.OUI.getId());
            message.addReaction(emote).queue();
        }
    }
}
