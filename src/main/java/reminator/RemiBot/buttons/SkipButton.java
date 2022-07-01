package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class SkipButton extends Button {
    public SkipButton() {
        super("skip", "Suivant", Emoji.fromUnicode("⏭️"));
    }

    @Override
    public void onClick(@NotNull ButtonClickEvent event) {
        if (!event.isFromGuild()) {
            new EnvoiMessage().sendPrivate(event.getUser(), "Tu ne peux pas cliquer sur ce bouton en privé.");
            return;
        }

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.getTrackScheduler().nextTrack();
    }
}
