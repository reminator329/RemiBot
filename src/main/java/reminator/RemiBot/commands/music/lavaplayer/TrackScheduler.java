package reminator.RemiBot.commands.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.buttons.BotButtons;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final Deque<AudioTrack> queue = new ArrayDeque<>();
    private final Deque<AudioTrack> history = new ArrayDeque<>();
    private MessageChannel displayChannel;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    public boolean queue(AudioTrack track) {

        // On ajoute dans la queue uniquement s'il y a déjà une musique en cours
        if (!this.player.startTrack(track, true)) {
            return this.queue.offer(track);
        } else {
            displayChannel(track);
        }
        return true;
    }

    public void nextTrack() {
        AudioTrack track = this.queue.poll();
        displayChannel(track);
        this.player.startTrack(track, false);
    }

    public boolean previousTrack() {
        AudioTrack currentTrack = this.player.getPlayingTrack();
        if(currentTrack == null && history.isEmpty()) {
            return false;
        }
        // Si on est à plus de 5s dans la musique actuelle, on remet la musique actuelle au début
        if (this.history.isEmpty() || currentTrack != null && currentTrack.getPosition() > 5000) {
            currentTrack.setPosition(0);
        } else {
            // Sinon on met la musique précédemment jouée et on ajoute la musique actuelle à la queue
            AudioTrack last = this.history.pollLast();
            this.player.startTrack(last, false); // ! Délchenche onTrachEnd qui ajoute la musique actuelle à l'historique
            if (currentTrack != null) {
                currentTrack.setPosition(0);
                this.queue.addFirst(currentTrack.makeClone());
                history.removeLast(); // Supprime la musique ajoutée par onTrackEnd
            }
        }
        return true;
    }

    private void displayChannel(AudioTrack track) {
        if (displayChannel != null) {
            new EnvoiMessage().withComponents(new BotButtons().withPlayingStatus(PlayingStatus.PLAY).NOW_PLAYING()).sendGuild(displayChannel, BotEmbed.NOW_PLAYING(track).build());
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
        history.add(track.makeClone());
    }

    public void clearQueue() {
        queue.clear();
        history.clear();
    }

    public Deque<AudioTrack> getQueue() {
        return queue;
    }

    public void shuffle() {
        List<AudioTrack> liste = new ArrayList<>(queue);
        Collections.shuffle(liste);
        queue.clear();
        queue.addAll(liste);
    }

    public void setDisplayChannel(MessageChannel channel) {
        displayChannel = channel;
    }
}
