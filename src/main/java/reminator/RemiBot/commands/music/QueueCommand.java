package reminator.RemiBot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

import static reminator.RemiBot.utils.TimeUtils.formatTime;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class QueueCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "queue";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"q"};
    }

    @Override
    public String getDescription() {
        return "Permet d'afficher les musiques à suivre.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        Guild guild = event.getGuild();

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        BlockingQueue<AudioTrack> queue = musicManager.getTrackScheduler().getQueue();

        if (queue.isEmpty()) {
            EnvoiMessage.sendMessage(event, "La queue est vide.");
            return;
        }

        int nbTracks = Math.min(queue.size(), 10);
        StringBuilder stringBuilder = new StringBuilder("**Musiques suivantes**\n");

        Iterator<AudioTrack> iterator = queue.iterator();
        for (int i = 0; i < nbTracks; i++) {
            AudioTrack track = iterator.next();
            AudioTrackInfo info = track.getInfo();

            stringBuilder
                    .append("`")
                    .append(i + 1)
                    .append(".` ")
                    .append(info.title)
                    .append(" de ")
                    .append(info.author)
                    .append(" | `")
                    .append(formatTime(track.getDuration()))
                    .append("`\n");
        }

        if (queue.size() > nbTracks) {
            stringBuilder.append("Et `")
                    .append(queue.size() - nbTracks)
                    .append("` autres...");
        }

        EnvoiMessage.sendMessage(event, stringBuilder.toString());
    }
}
