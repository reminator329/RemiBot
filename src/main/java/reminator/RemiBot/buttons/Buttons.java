package reminator.RemiBot.buttons;

import javax.annotation.Nullable;

public enum Buttons {

    BACK(new BackButton()),
    PAUSE(new PauseButton()),
    PLAY(new PlayButton()),
    SKIP(new SkipButton()),
    STOP(new StopButton()),
    SHUFFLE(new ShuffleButton()),
    CLEAR(new ClearButton()),
    ;

    private final Button button;

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
