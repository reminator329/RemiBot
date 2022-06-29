package reminator.RemiBot.commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.scans.Scan;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

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
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();

        StringBuilder msg = new StringBuilder();
        List<Scan> scans = priceScanner().getScans();
        int i = 0;
        for (Scan scan : scans) {
            msg.append(i++).append(") ").append(scan).append("\n");
        }
        channel.sendMessageEmbeds(new EmbedBuilder().setTitle("Scans ("+scans.size()+")").setDescription(msg.toString()).build()).queue();
    }
}
