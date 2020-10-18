package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.edt.Edt;

public class ProchainCoursCommand extends Command {

    public ProchainCoursCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("prochain-cours");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        return null;
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        Edt edt = new Edt();
        JSONObject o = edt.getNextCourse();
        String pCours = o.getString("summary");
        channel.sendMessage(pCours).queue();
    }
}
