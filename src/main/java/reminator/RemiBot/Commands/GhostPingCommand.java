package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;

public class GhostPingCommand extends Command {

    public GhostPingCommand () {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ghost-ping");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ghost ping");
        builder.appendDescription("Envoie le message que vous voulez sur tous les salons et les supprime");
        builder.addField("Signature", "`r!ghost-ping`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        event.getMessage().delete().queue();
        if (event.getMember() == null) {
            return;
        }
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage("Tu n'as pas la permission pour faire cette commande.").queue();
            //return;
        }
        Guild guild = event.getGuild();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        StringBuilder message = new StringBuilder();
        if (args.length < 2) {
            message = new StringBuilder("Coucou");
        } else {
            for (int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }
        }
        for (TextChannel channel : guild.getTextChannels()) {
            MessageAction action = channel.sendMessage(message);
            action.flatMap(Message::delete).queue();
        }
    }
}
