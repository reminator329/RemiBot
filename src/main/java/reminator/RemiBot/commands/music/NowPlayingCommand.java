package reminator.RemiBot.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import org.jsoup.select.Evaluator;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.concurrent.TimeUnit;

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

        EmbedBuilder builder = BotEmbed.MUSIQUE.getBuilder(playingTrack);
        EnvoiMessage.sendMessage(event, builder.build());
    }

    private String formatTime(long duration) {
        long hours = duration / TimeUnit.HOURS.toMillis(1);
        long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        long secondes = duration % TimeUnit.MILLISECONDS.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, secondes);
    }
}
