package reminator.RemiBot.Services.pricescanner.scans;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reminator.RemiBot.Services.pricescanner.Product;
import reminator.RemiBot.Services.pricescanner.update.ProductUpdate;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ComparerMalinScan implements Scan {
    private static final String URL = "https://www.comparez-malin.fr/informatique/pc-portable/?order=6&page=%d&p_max=1600&size_max=16&hdd_type=2&ssd_min=1000&ram_min=16384&ram_max=32768&cpuv[]=core-i5&cpuv[]=core-i7&cpuv[]=ryzen-7&cpuv[]=ryzen-9&gpuv[]=geforce-rtx-2060&gpuv[]=geforce-rtx-2070&gpuv[]=geforce-rtx-2080&gpuv[]=geforce-rtx-2070-super&gpuv[]=geforce-rtx-2080-super&gpuv[]=geforce-rtx-2060-max-q&gpuv[]=geforce-rtx-2070-max-q&gpuv[]=geforce-rtx-2080-max-q&gpuv[]=geforce-rtx-2070-super-max-q&gpuv[]=geforce-rtx-2080-super-max-q&gpuv[]=geforce-rtx-3050-max-q&gpuv[]=geforce-rtx-3050-ti-max-q&gpuv[]=geforce-rtx-3060-max-q&gpuv[]=geforce-rtx-3070-max-q&gpuv[]=geforce-rtx-3070-ti-max-q&gpuv[]=geforce-rtx-3080-max-q&gpuv[]=geforce-rtx-3080-ti-max-q&gpuv[]=geforce-rtx-3050&gpuv[]=geforce-rtx-3050-ti&gpuv[]=geforce-rtx-3060-max-p&gpuv[]=geforce-rtx-3070-max-p&gpuv[]=geforce-rtx-3070-ti-max-p";
    private Map<Integer, Product> products = new HashMap<>();

    @Override
    public boolean scan(TextChannel channel) throws IOException {
        boolean updated = false;

        boolean isFirstScan = this.products.isEmpty();

        List<MessageEmbed> embeds = new ArrayList<>();
        Map<Integer, Product> products = getAllProducts();

        List<ProductUpdate> updates = new ArrayList<>();

        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            int id = entry.getKey();
            Product product = entry.getValue();

            if (!this.products.containsKey(id)) {
                this.products.put(id, product);
                if(!isFirstScan) {
                    embeds.add(new EmbedBuilder().setDescription("Nouveau produit ! "+product+" :smiley:\n"+product.url).setColor(Color.GREEN).build());
                    updated = true;
                }
            } else {
                ProductUpdate update = this.products.get(id).update(product);
                if(update.hasChange()) {
                    embeds.add(update.toEmbed());
                    updated = true;
                    updates.add(update);
                }
            }
        }

        Iterator<Map.Entry<Integer, Product>> iterator = this.products.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer, Product> entry = iterator.next();
            int id = entry.getKey();
            Product product = entry.getValue();

            if(!products.containsKey(id)) {
                embeds.add(new EmbedBuilder().setDescription("Le "+product+" a disparu ! :frowning:").setColor(Color.RED).build());
                iterator.remove();
                updated = true;
            }
        }

        if(updated) {
            for(int i = 0; i < embeds.size(); i += 10) {
                channel.sendMessageEmbeds(embeds.subList(i, Math.min(i+10, embeds.size()))).queue();
            }
            channel.sendMessage("<@!368733622246834188>").queue();
        }

        return updated;
    }

    private Map<Integer, Product> getAllProducts() throws IOException {
        Map<Integer, Product> products = new HashMap<>();
        int page = 1;
        Document document;
        do {
            String url = URL.replace("%d", (page++) + "");
            document = Jsoup.connect(url).get();
            products.putAll(getProducts(document));
        } while (!document.select("a[aria-label=\"Next\"]").isEmpty());

        return products;
    }

    private Map<Integer, Product> getProducts(Document document) {
        Elements elements = document.select(".card.card-shadow.product");
        Map<Integer, Product> products = new HashMap<>();
        for (Element element : elements) {
            String idStr = element.id();
            if (idStr.isEmpty()) {
                continue;
            }
            Integer id = Integer.parseInt(idStr.split("_")[1]);
            products.put(id, getProduct(element));
        }
        return products;
    }

    private Product getProduct(Element element) {
        Elements link = element.select(".p-y-10 > h3 > a");
        String href = "https://www.comparez-malin.fr" + link.attr("href");
        String name = link.text();
        Element tag = element.selectFirst(".tag.tag-pill");
        String status = tag != null ? tag.text().trim() : "Disponible";
        float price = 0;
        if (status.equals("Disponible")) {
            price = Float.parseFloat(element.selectFirst(".product_price").attr("value"));
        }
        return new Product(name, href, price, status.equals("Disponible"));
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("**Comparer Malin** ("+products.size()+" produits)\n");
        if(products.size() < 30) {
            for (Product product : products.values()) {
                builder.append(product.price).append("€ - ").append(product.name).append("\n");
            }
        }
        return builder.toString();
    }
}
