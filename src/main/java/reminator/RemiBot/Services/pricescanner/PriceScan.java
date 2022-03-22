package reminator.RemiBot.Services.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import reminator.RemiBot.utils.HTTPRequest;

import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceScan implements Scan {
    public static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9,.]");

    public String mention;
    public String product;
    public String url;
    public String filter;
    public float lastPrice;

    public PriceScan(String mention, String url, String filter) throws IOException {
        this.mention = mention;
        this.filter = filter;
        this.url = url;

        lastPrice = retrievePrice();
    }

    public float retrievePrice() throws IOException {
        String rep = new HTTPRequest(url).GET();
        this.product = rep.split("<title>")[1].split("<")[0];
        String s = rep.split(filter)[1].split(">")[1].split("<")[0];

        Matcher m = NUMBER_PATTERN.matcher(s);

        StringBuilder price = new StringBuilder();

        while (m.find()) {
            price.append(m.group(0).replace(',', '.'));
        }

        return Float.parseFloat(price.toString());
    }

    @Override
    public boolean scan(TextChannel channel) throws IOException {
        float price = retrievePrice();
        if (price < lastPrice) {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setDescription("Le " + product + " a baissé de prix !! Il est passé de " + lastPrice + "€ à " + price + "€ !! :partying_face:\n" + url)
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
            channel.sendMessage(mention).queue();
            lastPrice = price;
            return true;
        } else if (price > lastPrice) {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setDescription("Le " + product + " a monté de prix ! Il est passé de " + lastPrice + "€ à " + price + "€ ! :frowning:\n" + url)
                    .setColor(Color.RED)
                    .build()
            ).queue();
            channel.sendMessage(mention).queue();
            lastPrice = price;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return product + " - " + url + " - Prix : " + lastPrice + "€";
    }
}
