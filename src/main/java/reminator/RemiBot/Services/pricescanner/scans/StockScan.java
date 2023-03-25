package reminator.RemiBot.Services.pricescanner.scans;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import reminator.RemiBot.Services.pricescanner.Product;
import reminator.RemiBot.Services.pricescanner.update.ProductUpdate;
import reminator.RemiBot.music.HTTPRequest;

import java.io.IOException;

public class StockScan implements Scan {
    public String mention;
    public String name;
    public Product product;
    public String string;
    public boolean shouldFind;

    public StockScan(String mention, String name, String url, boolean shouldFind, String string) throws IOException {
        this.mention = mention;
        this.string = string;
        this.shouldFind = shouldFind;
        this.product = new Product(name, url, null, inStock(url));
    }

    public boolean inStock(String url) throws IOException {
        boolean found = new HTTPRequest(url).GET().contains(string);
        return (found && shouldFind || !found && !shouldFind);
    }

    @Override
    public String getUrl() {
        return product.url;
    }

    @Override
    public boolean scan(TextChannel channel) throws IOException {
        boolean nextInStock = inStock(product.url);
        ProductUpdate update = product.updateInStock(nextInStock);
        if (update.hasChange()) {
            channel.sendMessageEmbeds(update.toEmbed()).queue();
            channel.sendMessage(mention).queue();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return product + " - " + product.url + " - En stock : " + product.inStock;
    }
}
