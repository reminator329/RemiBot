package reminator.RemiBot.commands.pricescanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.Services.pricescanner.scans.Scan;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

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
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

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
