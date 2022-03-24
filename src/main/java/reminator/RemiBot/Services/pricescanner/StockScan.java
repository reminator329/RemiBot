package reminator.RemiBot.Services.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import reminator.RemiBot.music.HTTPRequest;

import java.awt.*;
import java.io.IOException;

public class StockScan implements Scan {
    public String mention;
    public String url;
    public String product;
    public String toFind;
    public boolean inStock;

    public StockScan(String mention, String url, String product, String toFind) throws IOException {
        this.mention = mention;
        this.url = url;
        this.product = product;
        this.toFind = toFind;
        inStock = inStock();
    }

    public boolean inStock() throws IOException {
        return !(new HTTPRequest(url).GET().contains(toFind));
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean scan(TextChannel channel) throws IOException {
        boolean currentInStock = inStock();
        if (inStock != currentInStock) {
            if (currentInStock) {
                channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le " + product + " est en stock ! :partying_face: \n" + url).setColor(Color.GREEN).build()).queue();
                channel.sendMessage(mention).queue();
            } else {
                channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le " + product + " n'est plus en stock ! :frowning: \n" + url).setColor(Color.RED).build()).queue();
                channel.sendMessage(mention).queue();
            }
            inStock = currentInStock;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return product + " - " + url + " - En stock : "+inStock;
    }
}
