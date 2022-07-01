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
import java.util.function.Function;

import static reminator.RemiBot.utils.TimeUtils.formatTime;

public enum BotEmbed {

    BASE(o -> new EmbedBuilder()
            .setColor(RemiBot.color)),

    BASE_USER(o -> {
        if (!(o instanceof User user))
            return BotEmbed.BASE.getBuilder(null);
        return BotEmbed.BASE.getBuilder(null)
                .setFooter(user.getName(), user.getAvatarUrl());
    }),

    NOW_PLAYING(o -> {
        if (!(o instanceof AudioTrack track))
            return BotEmbed.BASE.getBuilder(null);

        TrackUserData data = (TrackUserData) track.getUserData();
        AudioTrackInfo info = track.getInfo();

        User requestedUser = data.getRequestedUser();
        User commandUser = data.getCommandUser();
        String avatarUrl = requestedUser.getAvatarUrl();

        return BASE_USER.getBuilder(commandUser)
                .setAuthor("Musique en cours", avatarUrl, avatarUrl)
                .setTitle("`" + info.title + "`", info.uri)

                .addField("Demandé par", requestedUser.getAsMention(), true)
                .addField("Auteur", info.author, true)
                .addField("Durée", formatTime(track.getDuration()), true);
    }),

    SPOTIFY(o -> {
        if (!(o instanceof Member member))
            return BotEmbed.BASE.getBuilder(null);
        EmbedBuilder builder = BotEmbed.BASE.getBuilder(null);
        List<Activity> activities = member.getActivities();
        for (Activity a : activities) {
            if (a.getName().equalsIgnoreCase("Spotify")) {
                addActivitySpotify(builder, a, member);
            }
        }
        return builder;
    }),
    ;

    private final Function<Object, EmbedBuilder> f;

    BotEmbed(Function<Object, EmbedBuilder> function) {
        f = function;
    }

    public EmbedBuilder getBuilder(Object o) {
        return f.apply(o);
    }

    private static void addActivitySpotify(EmbedBuilder builder, Activity a, Member member) {
        RichPresence rp = a.asRichPresence();
        if (rp != null) {
            try {
                builder.setImage(Objects.requireNonNull(rp.getLargeImage()).getUrl());
            } catch (NullPointerException ignored) {}
            String message = member.getUser().getName() + " écoute " + rp.getDetails() + " de " + rp.getState();
            builder.setFooter(message, member.getUser().getAvatarUrl());
        }
    }
}
