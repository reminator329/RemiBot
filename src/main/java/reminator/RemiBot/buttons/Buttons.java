package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import reminator.RemiBot.Services.reactionpersonne.Emotes;

import javax.annotation.Nullable;

public enum Buttons {

    BACK(new BackButton()),
    PAUSE(new PauseButton()),
    PLAY(new PlayButton()),
    SKIP(new SkipButton()),
    ;

    Button button;

    Buttons(Button b) {
        this.button = b;
    }

    @Nullable
    public static Button getButtonById(String id) {
        for (Buttons b : values()) {
            if (b.button.getId().equalsIgnoreCase(id)) {
                return b.button;
            }
        }
        return null;
    }

    public Button getButton() {
        return button;
    }
}
