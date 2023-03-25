package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;

public class ShuffleButton extends Button {
    public ShuffleButton() {
        super("shuffle", "", Emoji.fromUnicode("\uD83D\uDD00"));
    }

    @Override
    public void onClick(@NotNull ButtonInteractionEvent event) {

        Guild guild = event.getGuild();

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.getTrackScheduler().shuffle();
    }
}
