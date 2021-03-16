package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.Model.Devoir;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;

public class DevoirFiniCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirFiniCommand(){
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir-fini");
        this.addAlias("d-f");
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
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length < 2) {
            channel.sendMessage("Commande mal utilisé, voir `r!help devoir-fini`").queue();
            return;
        }

        int numeroDevoir;
        try {
            numeroDevoir = Integer.parseInt(args[1]);
        } catch (Exception e) {
            channel.sendMessage("Commande mal utilisé, voir `r!help devoir-fini`").queue();
            return;
        }

        Devoir devoir = bdDevoir.finiDevoir(event.getAuthor(), numeroDevoir);
        if (devoir == null) {
            channel.sendMessage("Erreur lors de la suppression du devoir. Vérifier le numéro du devoir `r!devoir`").queue();
        } else {
            channel.sendMessage("Le devoir " + devoir + " a bien été supprimé.").queue();
        }
    }
}
