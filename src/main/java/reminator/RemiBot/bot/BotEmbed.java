package reminator.RemiBot.bot;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.User;
import reminator.RemiBot.commands.music.TrackUserData;

import java.util.List;
import java.util.Objects;

import static reminator.RemiBot.utils.TimeUtils.formatTime;

public class BotEmbed {

    private BotEmbed() {
    }

    public static EmbedBuilder BASE() {
        return new EmbedBuilder()
                .setColor(RemiBot.color);
    }

    public static EmbedBuilder BASE_USER(User user) {
        if (user == null) {
            return BASE();
        }
        return BASE().setFooter(user.getName(), user.getAvatarUrl());
    }

    public static EmbedBuilder NOW_PLAYING(AudioTrack audioTrack) {
        if (audioTrack == null)
            return BASE();

        TrackUserData data = (TrackUserData) audioTrack.getUserData();
        AudioTrackInfo info = audioTrack.getInfo();

        User requestedUser = data.getRequestedUser();
        User commandUser = data.getCommandUser();
        String avatarUrl = requestedUser.getAvatarUrl();

        EmbedBuilder builder;

        if (commandUser == null) {
            builder = BASE();
        } else {
            builder = BASE_USER(commandUser);
        }

        return builder
                .setAuthor("Musique en cours", avatarUrl, avatarUrl)
                .setTitle("`" + info.title + "`", info.uri)

                .addField("Demandé par", requestedUser.getAsMention(), true)
                .addField("Auteur", info.author, true)
                .addField("Durée", formatTime(audioTrack.getDuration()), true)
                ;
    }

    public static EmbedBuilder SPOTIFY(Member member) {
        if (member == null)
            return BASE();

        EmbedBuilder builder = BASE_USER(member.getUser());
        List<Activity> activities = member.getActivities();
        for (Activity a : activities) {
            if (a.getName().equalsIgnoreCase("Spotify")) {
                addActivitySpotify(builder, a, member);
            }
        }
        return builder;
    }

    private static void addActivitySpotify(EmbedBuilder builder, Activity a, Member member) {
        RichPresence rp = a.asRichPresence();

        if (rp != null) {
            try {
                builder.setImage(Objects.requireNonNull(rp.getLargeImage()).getUrl());
            } catch (NullPointerException ignored) {
            }
            String message = rp.getDetails() + " de " + rp.getState();
            builder.setAuthor(message, "https://open.spotify.com/track/" + rp.getSyncId(), "https://cdn-icons-png.flaticon.com/512/2111/2111624.png");
        }
    }
}
