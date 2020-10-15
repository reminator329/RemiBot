package reminator.RemiBot.music;

import reminator.RemiBot.utils.InputStreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongsReader {
    private final File songsFile;

    public SongsReader(File songsFile) {
        this.songsFile = songsFile;
    }

    public List<Song> readSongs() throws IOException {
        ArrayList<Song> songs = new ArrayList<>();

        String[] songsStr = InputStreamUtils.readAsString(new FileInputStream(songsFile)).split("\n=============\n");
        for(String song : songsStr) {
            List<String> lines = new ArrayList<>(Arrays.asList(song.split("\n")));
            String title = lines.remove(0);
            String spotifyURL = lines.remove(0);
            songs.add(new Song(title, spotifyURL, lines));
        }
        return songs;
    }
}
