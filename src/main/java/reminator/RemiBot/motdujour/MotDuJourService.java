package reminator.RemiBot.motdujour;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class MotDuJourService {
    private static final String WORDS_PATH = "/words.json";
    private static final long _17_DECEMBER_2021 = 1639695600000L;
    private static final long DAY_MILLIS = 24 * 3600 * 1000;
    private static final List<String> channels = List.of("927508659037163610");

    private List<Word> words = new ArrayList<>();

    private final JDA discord;

    private boolean willSend = true;

    public MotDuJourService(JDA discord) throws IOException {
        this.discord = discord;

        String rawData = new String(getClass().getResourceAsStream(WORDS_PATH).readAllBytes(), StandardCharsets.UTF_8);
        JSONArray words = new JSONArray(rawData);
        for (int i = 0; i < words.length(); i++) {
            JSONObject word = words.getJSONObject(i);
            this.words.add(new Word(
                    word.getInt("id"),
                    word.getString("mot"),
                    word.getString("categorie"),
                    word.getString("description"),
                    word.getString("etymologie"),
                    word.getString("sources")
            ));
        }

        retrospective();
    }

    public void start() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int hours = new Date().getHours();
                if (willSend && hours == 0) {
                    sendWord();
                    willSend = false;
                } else if (hours != 0) {
                    willSend = true;
                }
            }
        }, 0, 3600 * 700);
    }

    public int getTodayWordIndex() {
        return (int) ((System.currentTimeMillis() - _17_DECEMBER_2021) / DAY_MILLIS);
    }

    public void retrospective() {
        long date = _17_DECEMBER_2021;
        while(date < System.currentTimeMillis()) {
            int index = (int) ((date - _17_DECEMBER_2021) / DAY_MILLIS);

            for (String channel : channels) {
                Word word = words.get(index);
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle(word.mot())
                        .appendDescription(word.description())
                        .addField("Source ", word.sources(), false)
                        .setFooter("Mot du jour " + new SimpleDateFormat("dd/MM/yyyy").format(new Date(date)));
                discord.getTextChannelById(channel).sendMessageEmbeds(embedBuilder.build()).queue();
            }

            date += DAY_MILLIS;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendWord() {
        for (String channel : channels) {
            Word word = words.get(getTodayWordIndex());
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle(word.mot())
                    .appendDescription(word.description())
                    .addField("Source ", word.sources(), false)
                    .setFooter("Mot du jour " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            discord.getTextChannelById(channel).sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
