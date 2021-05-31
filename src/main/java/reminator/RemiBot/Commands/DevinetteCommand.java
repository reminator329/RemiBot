package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.music.Song;
import reminator.RemiBot.music.SongsReader;
import reminator.RemiBot.utils.EnvoiMessage;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DevinetteCommand implements Command {

    private final static Random random = new Random();
    private List<Song> songs = new ArrayList<>();

    @Override
    public Category getCategory() {
        return Category.BILAL;
    }

    @Override
    public String getLabel() {
        return "devinette";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Trouve de quelle musique de Bilal Hassani provient la phrase affichée :musical_note:";
    }

    public DevinetteCommand() {
        SongsReader songsReader = new SongsReader(getClass().getResourceAsStream("/song/allSongs.txt"));
        try {
            songs = songsReader.readSongs();
            System.out.println("Songs : "+songs.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        final int[] tryAmount = {1};
        User user = event.getAuthor();

        long channelId = channel.getIdLong();
        long authorId = user.getIdLong();

        Song song = getRandomSong();

        String randomLyricsLine = song.getRandomLyricsLine();
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("De quelle musique provient cette phrase ? :face_with_monocle:\nÉcrit 'jsp' si tu sais pas.").setDescription(randomLyricsLine)
                .setFooter(user.getName(), user.getAvatarUrl())
        .setThumbnail("https://cdn.discordapp.com/attachments/496020487797735435/766374212189028372/unknown.png");

        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;

                String msg = event.getMessage().getContentRaw();

                if(song.getTitle().equalsIgnoreCase(msg)) {
                    EnvoiMessage.sendMessage(event, "Oui !! C'était bien **"+song.getTitle()+"** ! Tu as trouvé en "+tryAmount[0]+" essai(s). Félicitations ! :partying_face: :heart:\n"+song.getSpotifyUrl());
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(msg.equalsIgnoreCase("jsp")) {
                    EnvoiMessage.sendMessage(event, "Dommaage :sob: ! La chanson était **"+song.getTitle()+"** !!\n"+song.getSpotifyUrl());
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(msg.equalsIgnoreCase("r!devinette")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                tryAmount[0]++;

                EnvoiMessage.sendMessage(event, "Nan ! C'est pas ça ! Essaie encore !");
            }
        });

        EnvoiMessage.sendMessage(event, embedBuilder.build());
    }

    private Song getRandomSong() {
        return songs.get(random.nextInt(songs.size()));
    }
}
