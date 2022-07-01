package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;
import reminator.RemiBot.commands.music.lavaplayer.PlayingStatus;

import java.util.ArrayList;
import java.util.List;

public class BotButtons {

    private PlayingStatus playingStatus;

    public BotButtons() {
        playingStatus = PlayingStatus.PAUSE;
    }

    public List<List<Component>> NOW_PLAYING() {
        List<List<Component>> buttons = new ArrayList<>();

        List<Component> buttons1 = new ArrayList<>();
        buttons1.add(Buttons.BACK.getButton());
        switch (playingStatus) {
            case PLAY -> buttons1.add(Buttons.PAUSE.getButton());
            case PAUSE -> buttons1.add(Buttons.PLAY.getButton());
        }
        buttons1.add(Buttons.SKIP.getButton());

        buttons.add(buttons1);
        return buttons;
    }

    public BotButtons withPlayingStatus(PlayingStatus playingStatus) {
        this.playingStatus = playingStatus;
        return this;
    }
}
