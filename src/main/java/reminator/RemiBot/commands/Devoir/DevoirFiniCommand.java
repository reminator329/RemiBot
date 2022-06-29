package reminator.RemiBot.commands.Devoir;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.Devoir.Model.BDDevoir;
import reminator.RemiBot.commands.Devoir.Model.BDDevoirJson;
import reminator.RemiBot.commands.Devoir.Model.Devoir;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class DevoirFiniCommand implements Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    @Override
    public Category getCategory() {
        return Category.DEVOIR;
    }

    @Override
    public String getLabel() {
        return "devoir-fini";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"df", "d-f"};
    }

    @Override
    public String getDescription() {
        return "Supprime un devoir de votre liste de devoirs";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <numéro du devoir>";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();

        if (args.size() < 1) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisé, voir `r!help devoir-fini`");
            return;
        }

        int numeroDevoir;
        try {
            numeroDevoir = Integer.parseInt(args.get(0));
        } catch (Exception e) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisé, voir `r!help devoir-fini`");
            return;
        }

        Devoir devoir = bdDevoir.finiDevoir(event.getAuthor(), numeroDevoir);
        if (devoir == null) {
            EnvoiMessage.sendMessage(event, "Erreur lors de la suppression du devoir. Vérifier le numéro du devoir `r!devoir`");
        } else {
            EnvoiMessage.sendMessage(event, "Le devoir " + devoir + " a bien été supprimé.");
        }
    }
}
