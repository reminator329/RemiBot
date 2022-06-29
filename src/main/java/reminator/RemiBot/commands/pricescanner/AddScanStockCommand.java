package reminator.RemiBot.commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.scans.StockScan;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
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
        return Command.super.getSignature() + " <produit> <url> <has|hasn't> <chaîne à trouver ou non>";
    }

    /**
     * r!add-scan https://www.rueducommerce.fr/p-rog-zephyrus-g14-ga401qm-023t-gris-asus-3206677-28409.html?articleOfferId=21466288
     * cut:class="dyn_prod_price product__price--new"
     *
     * r!add-stock https://www.lenovo.com/fr/fr/cart/dr/stock?code=82K800EVFR "Lenovo Slim 7" "En rupture de stock"
     * @param event
     */
    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        try {
            String str = String.join(" ", args);
            List<String> parts = new StringReader(str).readStrings();
            if(parts.get(1).toLowerCase().matches("^(has|hasn't)$")) {
                channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Erreur : has ou hasn't.").setColor(Color.RED).build()).queue();
                return;
            }
            StockScan scan = new StockScan(author.getAsMention(), parts.get(0), parts.get(1), parts.get(2).equalsIgnoreCase("has"), parts.get(3));
            priceScanner().addScan(scan);
            priceScanner().save();
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le scan a bien été ajouté :ok_hand: (En stock : "+scan.product.inStock+")").setColor(Color.GREEN).build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            channel.sendMessage("Une erreur est survenue.\n```"+e.getMessage()+"```").queue();
        }
    }
}
