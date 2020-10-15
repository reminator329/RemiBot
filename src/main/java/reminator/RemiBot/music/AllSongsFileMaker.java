package reminator.RemiBot.music;


import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AllSongsFileMaker {
    /**
     * https://developer.spotify.com/console/get-search-item/?q=abba&type=track&market=US -> GET TOKEN
     */
    private static final String oAuthToken = "";

    public static void main(String[] args) throws IOException {
        File allMusics = new File("allSongs.txt");
        if (!allMusics.exists()) {
            allMusics.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(allMusics);
        Set<String> allSongsURL = getAllSongsURL();
        int i = 0;
        int total = allSongsURL.size();
        for(String songURL : allSongsURL) {
            i++;
            Song song = getSongFromURL(songURL);
            if(song == null) {
                total--;
                i--;
                continue;
            }

            String spotifyURL;
            try {
                if(song.getTitle().contains("Bilal Hassani")) {
                    throw new RuntimeException();
                }
                String get = new HTTPRequest("https://api.spotify.com/v1/search?q=" + song.getTitle().replaceAll(" ", "%20") + "%20Bilal%20Hassani&type=track&market=FR&limit=1&offset=0")
                        .withHeader("Accept", "application/json")
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Authorization", "Bearer "+oAuthToken)
                        .GET();
                spotifyURL = new JSONObject(get)
                        .getJSONObject("tracks")
                        .getJSONArray("items")
                        .getJSONObject(0)
                        .getJSONObject("external_urls")
                        .getString("spotify");
            }catch (Exception e) {
                System.out.println("Failed : "+song.getTitle());
                continue;
            }

            fileOutputStream.write((song.getTitle()+"\n").getBytes(StandardCharsets.UTF_8));
            fileOutputStream.write((spotifyURL+"\n").getBytes(StandardCharsets.UTF_8));
            fileOutputStream.write(song.getLyricsAsString().replaceAll(" ", " ").getBytes(StandardCharsets.UTF_8));
            fileOutputStream.write("\n=============\n".getBytes(StandardCharsets.UTF_8));
            System.out.println("Processed "+song.getTitle());
            System.out.println("Progress : "+i+"/"+total);
        }
        fileOutputStream.close();
    }



    private static Set<String> getAllSongsURL() throws IOException {
        String pageContent = new HTTPRequest("https://www.paroles.net/bilal-hassani").GET();
        pageContent = pageContent.replaceAll(">(.*)<", ">\n$1\n<")
                .replaceAll(">(.)", ">\n$1")
                .replaceAll("(.)<", "$1\n<");
        String[] lines = pageContent.split("\n");
        return Arrays.stream(lines)
                .filter(str -> str.contains("bilal-hassani/paroles-"))
                .map(str -> str.split("href=")[1].split(">")[0])
                .collect(Collectors.toSet());
    }

    private static Song getSongFromURL(String url) throws IOException {
        String pageContent = new HTTPRequest(url).GET();
        pageContent = pageContent.split("<div class=song-text>")[1].split("</div><br><div class=pw-server-widget")[0];
        pageContent = pageContent.replaceAll(">(.*)<", ">\n$1\n<")
                .replaceAll(">(.)", ">\n$1")
                .replaceAll("(.)<", "$1\n<");
        String[] lines = pageContent.split("\n");

        List<String> stringList = Arrays.stream(lines)
                .filter(str -> !str.matches(".*([<>=|;{}\\[\\]]|[a-zA-Z]\\().*"))
                .collect(Collectors.toList());
        String title = stringList.remove(0).replaceFirst("Paroles de la chanson (.*) par Bilal Hassani", "$1");
        if(title.contains("pas encore")) {
            return null;
        }
        return new Song(title, stringList);
    }
}
