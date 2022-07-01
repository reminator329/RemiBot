package reminator.RemiBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class NowPlayingCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "now-playing";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"now", "np"};
    }

    @Override
    public String getDescription() {
        return "Permet d'afficher les détails de la musique en cours.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        Guild guild = event.getGuild();

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        AudioPlayer audioPlayer = musicManager.getAudioPlayer();
        AudioTrack playingTrack = audioPlayer.getPlayingTrack();

        if (playingTrack == null) {
            EnvoiMessage.sendMessage(event, "Il n'y a pas de musique en cours.");
            return;
        }

        playingTrack.setUserData(((TrackUserData) playingTrack.getUserData()).withCommandUser(event.getMember().getUser()));

        EmbedBuilder builder = BotEmbed.NOW_PLAYING.getBuilder(playingTrack);
        EnvoiMessage.sendMessage(event, builder.build());
    }
}
