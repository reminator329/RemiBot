package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class ClearButton extends Button {
    public ClearButton() {
        super("clear", "", Emoji.fromUnicode("\uD83D\uDDD1️"));
    }

    @Override
    public void onClick(@NotNull ButtonClickEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.getTrackScheduler().clearQueue();
        new EnvoiMessage().sendGuild(event.getChannel(), "La queue a été supprimé par " + event.getUser().getAsMention());
    }
}
