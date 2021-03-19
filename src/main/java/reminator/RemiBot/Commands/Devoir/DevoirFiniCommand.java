package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.Model.Devoir;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;

public class DevoirFiniCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirFiniCommand(){
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir-fini");
        this.addAlias("d-f");
        this.addAlias("df");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devoir-fini");
        builder.appendDescription("Supprime un devoir de votre liste de devoirs");
        builder.addField("Signature", "`r!devoir-fini <numéro du devoir>`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length < 2) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisé, voir `r!help devoir-fini`");
            return;
        }

        int numeroDevoir;
        try {
            numeroDevoir = Integer.parseInt(args[1]);
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
