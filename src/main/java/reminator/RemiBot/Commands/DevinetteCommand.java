package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.music.Song;
import reminator.RemiBot.music.SongsReader;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DevinetteCommand extends Command {
    private final static Random random = new Random();

    private List<Song> songs = new ArrayList<>();

    public DevinetteCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devinette");
        this.setHelp(setHelp());
        SongsReader songsReader = new SongsReader(getClass().getResourceAsStream("/song/allSongs.txt"));
        try {
            songs = songsReader.readSongs();
            System.out.println("Songs : "+songs.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devinette");
        builder.appendDescription("Trouve de quelle musique de Bilal Hassani provient la phrase affichée :musical_note:");

        builder.addField("Signature", "`r!devinette`", false);

        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event1) {
        MessageChannel channel = event1.getChannel();

        final int[] tryAmount = {1};
        User user = event1.getAuthor();

        long channelId = channel.getIdLong();
        long authorId = user.getIdLong();

        Song song = getRandomSong();

        String randomLyricsLine = song.getRandomLyricsLine();
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("De quelle musique provient cette phrase ? :face_with_monocle:\nÉcrit 'jsp' si tu sais pas.").setDescription(randomLyricsLine)
                .setFooter(user.getName(), user.getAvatarUrl())
        .setThumbnail("https://cdn.discordapp.com/attachments/496020487797735435/766374212189028372/unknown.png");

        event1.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;

                String msg = event.getMessage().getContentRaw();

                if(song.getTitle().equalsIgnoreCase(msg)) {
                    channel.sendMessage("Oui !! C'était bien **"+song.getTitle()+"** ! Tu as trouvé en "+tryAmount[0]+" essai(s). Félicitations ! :partying_face: :heart:\n"+song.getSpotifyUrl()).queue();
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(msg.equalsIgnoreCase("jsp")) {
                    channel.sendMessage("Dommaage :sob: ! La chanson était **"+song.getTitle()+"** !!\n"+song.getSpotifyUrl()).queue();
                    event.getJDA().removeEventListener(this);
                    return;
                }

                if(msg.equalsIgnoreCase("r!devinette")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                tryAmount[0]++;

                channel.sendMessage("Nan ! C'est pas ça ! Essaie encore !").queue();
            }
        });

        channel.sendMessage(embedBuilder.build()).queue();
    }

    private Song getRandomSong() {
        return songs.get(random.nextInt(songs.size()));
    }
}
