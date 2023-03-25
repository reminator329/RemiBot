package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import reminator.RemiBot.commands.music.lavaplayer.PlayingStatus;

import java.util.ArrayList;
import java.util.List;

public class BotButtons {

    private PlayingStatus playingStatus;

    public BotButtons() {
        playingStatus = PlayingStatus.PAUSE;
    }

    public List<List<ItemComponent>> NOW_PLAYING() {
        List<List<ItemComponent>> buttons = new ArrayList<>();

        List<ItemComponent> buttons1 = new ArrayList<>();
        buttons1.add(Buttons.BACK.getButton());
        switch (playingStatus) {
            case PLAY -> buttons1.add(Buttons.PAUSE.getButton());
            case PAUSE -> buttons1.add(Buttons.PLAY.getButton());
        }
        buttons1.add(Buttons.SKIP.getButton());

        List<ItemComponent> buttons2 = new ArrayList<>();
        buttons2.add(Buttons.SHUFFLE.getButton());
        buttons2.add(Buttons.CLEAR.getButton());

        buttons.add(buttons1);
        buttons.add(buttons2);
        return buttons;
    }

    public BotButtons withPlayingStatus(PlayingStatus playingStatus) {
        this.playingStatus = playingStatus;
        return this;
    }
}
