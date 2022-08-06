package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class BackButton extends Button {

    public BackButton() {
        super("back", "", Emoji.fromUnicode("⏮️"));
    }
    @Override
    public void onClick(@NotNull ButtonClickEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        new EnvoiMessage().sendGuild(event.getChannel(), event.getUser().getAsMention() + " est retourné à la musique précédente.");
        musicManager.getTrackScheduler().previousTrack();
    }
}
