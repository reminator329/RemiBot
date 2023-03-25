package reminator.RemiBot.commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.scans.PriceScan;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.StringReader;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static reminator.RemiBot.Services.pricescanner.PriceScannerService.priceScanner;

public class AddScanCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.SCANNER;
    }

    @Override
    public String getLabel() {
        return "add-scan";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Add un scan de prix";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <produit> <url> <cut>";
    }

    /**
     * r!add-scan https://www.rueducommerce.fr/p-rog-zephyrus-g14-ga401qm-023t-gris-asus-3206677-28409.html?articleOfferId=21466288
     * cut:class="dyn_prod_price product__price--new"
     *
     * r!add-scan stock https://www.lenovo.com/fr/fr/cart/dr/stock?code=82K800EVFR "Lenovo Slim 7" "en stock"
     * @param event
     */
    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();
        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();

        try {
            System.out.println(args);
            StringReader reader = new StringReader(String.join(" ", args));
            List<String> strings = reader.readStrings();
            System.out.println(strings);
            PriceScan scan = new PriceScan(author.getAsMention(), strings.get(0), strings.get(1), strings.get(2));
            priceScanner().addScan(scan);
            priceScanner().save();
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le scan a bien été ajouté :ok_hand: (Prix détecté : "+scan.product.price+")").setColor(Color.GREEN).build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            channel.sendMessage("Une erreur est survenue.\n```"+e.getMessage()+"```").queue();
        }
    }
}
