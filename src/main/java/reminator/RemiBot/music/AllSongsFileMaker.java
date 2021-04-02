package reminator.RemiBot.music;


import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AllSongsFileMaker {
    /**
     * https://developer.spotify.com/console/get-search-item/?q=abba&type=track&market=US -> GET TOKEN
     */
    private static final String oAuthToken = "BQDCjN8r354rTFdrzMhpa8RDamxlPwIA96H8XfQPyYPG1bCAJa6oBLBc9GHgw0rnh3RO7iZwK2pNOTOi51SZF_d_HrZFuGHsMHBYfdvWUew3sZNCCDrRE5EQqpTgqhsNOhX0wJubPA";

    public static void main(String[] args) throws IOException, InterruptedException {
        File allMusics = new File("src/main/resources/song/allSongs.txt");
        if (!allMusics.exists()) {
            allMusics.getParentFile().mkdirs();
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

            String spotifyURL = "null";
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
                System.out.println("Failed to get spotify URL for : "+songURL+" ("+song.getTitle()+")");
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



    private static Set<String> getAllSongsURL() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(HttpRequest.newBuilder(URI.create("https://www.paroles.net/bilal-hassani")).GET().build(), HttpResponse.BodyHandlers.ofString());
        String pageContent = response.body();
//        String pageContent = new HTTPRequest("https://www.paroles.net/bilal-hassani").GET();
        pageContent = pageContent.replaceAll(">(.*)<", ">\n$1\n<")
                .replaceAll(">(.)", ">\n$1")
                .replaceAll("(.)<", "$1\n<");
        String[] lines = pageContent.split("\n");
        return Arrays.stream(lines)
                .filter(str -> str.contains("bilal-hassani/paroles-"))
                .map(str -> "https://www.paroles.net"+str.split("href=\"")[1].split("\"")[0])
                .collect(Collectors.toSet());
    }

    private final static Pattern PATTERN = Pattern.compile("<[^>]+>");

    private static Song getSongFromURL(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.ALWAYS)
        .build();
        String pageContent = new HTTPRequest(url)
                .withHeader("User-Agent", "curl/7.55.1")
                .GET();
//        HttpResponse<String> response = client.send(HttpRequest.newBuilder(URI.create(url))
//                .header("Host", "www.paroles.net")
//                .header("Content-Length", "13")
//                .header("User-Agent", "curl/7.55.1")
//                .header("Accept", "*/*")
//                .GET().build(), HttpResponse.BodyHandlers.ofString());
//        String pageContent = response.body();
//        String pageContent = new HTTPRequest(url).GET();
        pageContent = pageContent.split("<div class=\"song-text\">")[1]
                .split("<div class=\"pw-server-widget\"")[0];
        pageContent = pageContent.replaceAll(">(.*)<", ">\n$1\n<")
                .replaceAll(">(.)", ">\n$1")
                .replaceAll("(.)<", "$1\n<");
        String[] lines = pageContent.split("\n");

        List<String> stringList = Arrays.stream(lines)
                .filter(line -> {
                    Matcher matcher = PATTERN.matcher(line);
                    return line.trim().length() > 0 && matcher.results().count() <= 1 && !line.matches("<[^>]+>");
                })
                .collect(Collectors.toList());
        String title = stringList.remove(0).replaceFirst("Paroles de la chanson (.*) par Bilal Hassani", "$1");
        if(title.contains("pas encore")) {
            return null;
        }
        return new Song(title, stringList);
    }
}
