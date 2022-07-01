package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class StopButton extends Button {
    public StopButton() {
        super("stop", "", Emoji.fromUnicode("⏹️"));
    }

    @Override
    public void onClick(@NotNull ButtonClickEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.stop();
        new EnvoiMessage().sendGuild(event.getChannel(), event.getUser().getAsMention() + " a vidé la queue et arrêté la lecture.");
    }
}
