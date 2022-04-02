package reminator.RemiBot.Commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.scans.Scan;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static reminator.RemiBot.Services.pricescanner.PriceScannerService.priceScanner;

public class RemScanCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.SCANNER;
    }

    @Override
    public String getLabel() {
        return "rem-scan";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Supprime un scan de prix";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <indice>";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        try {
            int i = Integer.parseInt(args.get(0));
            Scan scan = priceScanner().getScans().remove(i);
            priceScanner().save();
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("Le scan "+scan+"a bien été supprimé :ok_hand:").setColor(Color.GREEN).build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            channel.sendMessage("Une erreur est survenue.\n```"+e.getMessage()+"```").queue();
        }
    }
}
