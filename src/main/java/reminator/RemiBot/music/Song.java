package reminator.RemiBot.music;

import java.util.List;
import java.util.Random;

public class Song {
    private static final Random random = new Random();

    private final String title;
    private final String spotifyUrl;
    private final List<String> lyrics;

    public Song(String title, List<String> lyrics) {
        this(title, null, lyrics);
    }

    public Song(String title, String spotifyUrl, List<String> lyrics) {
        this.title = title.replace("Bea", "Bae").replace("Belek", "Beleck");
        this.lyrics = lyrics;
        if(spotifyUrl.equals("null")) {
            this.spotifyUrl = "";
        }else{
            this.spotifyUrl = spotifyUrl;
        }
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLyrics() {
        return lyrics;
    }

    public String getRandomLyricsLine() {
        return lyrics.get(random.nextInt(lyrics.size()));
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public String getLyricsAsString() {
        return String.join("\n", lyrics);
    }
}
