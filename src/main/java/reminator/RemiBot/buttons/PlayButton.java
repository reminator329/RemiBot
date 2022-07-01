package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class PlayButton extends Button {

    public PlayButton() {
        super("play", "", Emoji.fromUnicode("▶️"));
    }

    @Override
    public void onClick(@NotNull ButtonClickEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.getAudioPlayer().setPaused(false);
        event.getInteraction().editButton(Buttons.PAUSE.getButton()).queue();
        new EnvoiMessage().sendGuild(event.getChannel(), event.getUser().getAsMention() + " a repris la lecture.");
    }
}
