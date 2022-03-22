package reminator.RemiBot.Commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.StockScan;
import reminator.RemiBot.utils.StringReader;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static reminator.RemiBot.Services.pricescanner.PriceScannerService.priceScanner;

public class AddScanStockCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.SCANNER;
    }

    @Override
    public String getLabel() {
        return "add-stock";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Add un scan de stock";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <url> <produit> <chaîne à ne faut pas trouver>";
    }

    /**
     * r!add-scan https://www.rueducommerce.fr/p-rog-zephyrus-g14-ga401qm-023t-gris-asus-3206677-28409.html?articleOfferId=21466288
     * cut:class="dyn_prod_price product__price--new"
     *
     * r!add-stock https://www.lenovo.com/fr/fr/cart/dr/stock?code=82K800EVFR "Lenovo Slim 7" "En rupture de stock"
     * @param event
     * @param author
     * @param channel
     * @param args
     */
    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        try {
            String str = String.join(" ", args);
            List<String> parts = new StringReader(str).readStrings();
            StockScan scan = new StockScan(author.getAsMention(), parts.get(0), parts.get(1), parts.get(2));
            priceScanner().addScan(scan);
            priceScanner().save();
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le scan a bien été ajouté :ok_hand: (En stock : "+scan.inStock+")").setColor(Color.GREEN).build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            channel.sendMessage("Une erreur est survenue.\n```"+e.getMessage()+"```").queue();
        }
    }
}
