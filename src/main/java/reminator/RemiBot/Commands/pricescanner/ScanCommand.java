package reminator.RemiBot.Commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;

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
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Lancement du scan ("+priceScanner().getScans().size()+")...").build()).queue();
        priceScanner().scan().thenRun(() -> {
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Scan terminé !").setColor(Color.GREEN).build()).queue();
        });
    }
}
