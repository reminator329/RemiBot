package reminator.RemiBot.commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

import java.awt.*;
import java.util.List;

import static reminator.RemiBot.Services.pricescanner.PriceScannerService.priceScanner;

public class ScanCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.SCANNER;
    }

    @Override
    public String getLabel() {
        return "scan";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Lance les scans enregistrés.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();

        channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Lancement du scan ("+priceScanner().getScans().size()+")...").build()).queue();
        priceScanner().scan().thenRun(() -> {
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Scan terminé !").setColor(Color.GREEN).build()).queue();
        });
    }
}
