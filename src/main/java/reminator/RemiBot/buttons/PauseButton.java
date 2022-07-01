package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;

public class PauseButton extends Button {
    public PauseButton() {
        super("pause", "Pause", Emoji.fromUnicode("⏸️"));
    }
}
