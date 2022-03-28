package reminator.RemiBot.Commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.Scan;

import java.util.List;

import static reminator.RemiBot.Services.pricescanner.PriceScannerService.priceScanner;

public class ScanListCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.SCANNER;
    }

    @Override
    public String getLabel() {
        return "scan-list";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Liste les scans de prix enregistrés.";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        StringBuilder msg = new StringBuilder();
        List<Scan> scans = priceScanner().getScans();
        int i = 0;
        for (Scan scan : scans) {
            msg.append(i++).append(") ").append(scan).append("\n");
        }
        channel.sendMessageEmbeds(new EmbedBuilder().setTitle("Scans ("+scans.size()+")").setDescription(msg.toString()).build()).queue();
    }
}
