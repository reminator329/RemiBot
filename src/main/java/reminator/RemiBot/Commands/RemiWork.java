package reminator.RemiBot.Commands;

import de.svenjacobs.loremipsum.LoremIpsum;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.bot.RemiBot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RemiWork implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    private Timer timerWork;
    private Timer timerSpam;
    private LoremIpsum lorem;

    @Override
    public Category getCategory() {
        return Category.PERSO;
    }

    @Override
    public String getLabel() {
        return "remi-work";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"r-w", "rw"};
    }

    @Override
    public String getDescription() {
        return "Spam work Rémi.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        if (event.getAuthor().getIdLong() != 368733622246834188L) {
            event.getChannel().sendMessage("Commande réservée à Rémi !").queue();
            return;
        }

        if (ecoutes.contains(channel)) {
            channel.sendMessage("Arrêt du spam").queue();
            ecoutes.remove(channel);
            timerWork.cancel();
            timerWork.purge();
        } else {
            channel.sendMessage("Début du spam").queue();
            ecoutes.add(channel);
            timerWork = new Timer();
            timerWork.schedule(new TimerTask() {
                @Override
                public void run() {
                    String content = "!work";
                    String nonce = "";
                    String payload = "{\"content\": \"" + content + "\", \"nonce\": \"" + nonce + "\", \"tts\": \"false\"}";
                    String idChannel = channel.getId();
                    String requestUrl="https://discord.com/api/v9/channels/" + idChannel + "/messages";
                    String retour = sendPostRequest(requestUrl, payload);
                    System.out.println(retour);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    content = "!dep all";
                    payload = "{\"content\": \"" + content + "\", \"nonce\": \"" + nonce + "\", \"tts\": \"false\"}";
                    sendPostRequest(requestUrl, payload);
                }
            }, 0, 4 * 60 * 1000 + 1);

            timerSpam = new Timer();
            timerSpam.schedule(new TimerTask() {
                @Override
                public void run() {
                    String content = lorem.getWords(1, 2);
                    String nonce = "";
                    String payload = "{\"content\": \"" + content + "\", \"nonce\": \"" + nonce + "\", \"tts\": \"false\"}";
                    String idChannel = channel.getId();
                    String requestUrl="https://discord.com/api/v9/channels/" + idChannel + "/messages";
                    String retour = sendPostRequest(requestUrl, payload);
                    System.out.println(retour);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    content = "!dep all";
                    payload = "{\"content\": \"" + content + "\", \"nonce\": \"" + nonce + "\", \"tts\": \"false\"}";
                    sendPostRequest(requestUrl, payload);
                }
            }, 0, 60 * 1000 + 1);
        }
    }

    public String sendPostRequest(String requestUrl, String payload) {
        StringBuilder jsonString = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("authority", "discord.com");
            connection.setRequestProperty("authorization", RemiBot.AUTHORIZATION_REMI);
            connection.setRequestProperty("content-length", String.valueOf(payload.length()));
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            System.out.println("bonjour");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("test");
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();

            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return jsonString.toString();
    }
}
