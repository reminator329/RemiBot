package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;

public class PlayButton extends Button {

    public PlayButton() {
        super("play", "Play", Emoji.fromUnicode("▶️"));
    }
}
