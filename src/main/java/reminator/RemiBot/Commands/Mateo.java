package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;
import reminator.RemiBot.utils.HTTPRequest;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Mateo extends Command {

    private static boolean execute = false;
    Timer timer;

    public Mateo() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("mateo");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        return null;
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != 368733622246834188L && event.getAuthor().getIdLong() != 329712193249476609L) {
            event.getChannel().sendMessage("Commande réservée à Matéo !").queue();
            return;
        }
        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();

        if (execute) {
            channel.sendMessage("Arrêt de la commande").queue();
            timer.cancel();
            timer.purge();
            execute = false;
        } else {
            channel.sendMessage("Début de la commande").queue();
            final String[] idSave = {""};

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {


                    try {
                        String p = new HTTPRequest("https://twitter.com/i/api/2/timeline/conversation/1392212382831816705.json?include_profile_interstitial_type=1&include_blocking=1&include_blocked_by=1&include_followed_by=1&include_want_retweets=1&include_mute_edge=1&include_can_dm=1&include_can_media_tag=1&skip_status=1&cards_platform=Web-12&include_cards=1&include_ext_alt_text=true&include_quote_count=true&include_reply_count=1&tweet_mode=extended&include_entities=true&include_user_entities=true&include_ext_media_color=true&include_ext_media_availability=true&send_error_codes=true&simple_quoted_tweet=true&count=20&include_ext_has_birdwatch_notes=false&ext=mediaStats%2ChighlightedLabel")
                                .withHeader("accept-language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7")
                                .withHeader("authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANRILgAAAAAAnNwIzUejRCOuH5E6I8xnZz4puTs%3D1Zv7ttfk8LF81IUq16cHjhLTvJu4FA33AGWWjCpTnA")
                                .withHeader("cookie", "_sl=1; personalization_id=\"v1_oRZugRsMECI++UDH3RMehQ==\"; guest_id=v1%3A162085596435924474; ct0=a4014bb283e5dcffdd9e1a5070026109; _twitter_sess=BAh7CSIKZmxhc2hJQzonQWN0aW9uQ29udHJvbGxlcjo6Rmxhc2g6OkZsYXNo%250ASGFzaHsABjoKQHVzZWR7ADoPY3JlYXRlZF9hdGwrCExKi2J5AToMY3NyZl9p%250AZCIlM2QwMWI3OWFiNmVmMmFhYmEwYmMxODk1OWU5YTczMTc6B2lkIiVlM2Nl%250AMzZiMjRhM2Q0ZGJmY2Q1M2IxZTdkNjVhZGViOA%253D%253D--678ba1feeb5cebc249025a654fe16fdddae3a3e2; gt=1392596973203693578")
                                .withHeader("referer", "https://twitter.com/JulieOudet/status/1392212382831816705")
                                .withHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                                .withHeader("sec-ch-ua-mobile", "?0")
                                .withHeader("sec-fetch-dest", "empty")
                                .withHeader("sec-fetch-mode", "cors")
                                .withHeader("sec-fetch-site", "same-origin")
                                .withHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                                .withHeader("x-csrf-token", "a4014bb283e5dcffdd9e1a5070026109")
                                .withHeader("x-guest-token", "1392596973203693578")
                                .withHeader("x-twitter-active-user", "yes")
                                .withHeader("x-twitter-client-language", "fr")
                                .GET();

                        JSONObject page = new JSONObject(p);
                        String conversation = page.getJSONObject("timeline").getString("id");
                        String id = conversation.split("-")[1];
                        if (!idSave[0].equals(id)) {
                            idSave[0] = id;
                            JSONObject tweet = page.getJSONObject("globalObjects").getJSONObject("tweets").getJSONObject(id);


                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setColor(Color.RED);
                            builder.setTitle("Julie Oudet");
                            builder.addField("Nouveau / dernier tweet", tweet.getString("full_text"), false);
                            builder.setFooter(user.getName(), user.getAvatarUrl());
                            channel.sendMessage(builder.build()).queue();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 5000);
        }
    }
}
