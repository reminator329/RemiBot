package reminator.RemiBot.commands.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.MessageChannel;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private MessageChannel displayChannel;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingDeque<>();
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

    private void displayChannel(AudioTrack track) {
        if (displayChannel != null) {
            EnvoiMessage.sendGuild(displayChannel, BotEmbed.NOW_PLAYING(track).build());
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public void clearQueue() {
        queue.clear();
    }

    public BlockingQueue<AudioTrack> getQueue() {
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
