package reminator.RemiBot.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class GhostPingCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "ghost-ping";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"g-p", "gp"};
    }

    @Override
    public String getDescription() {
        return "Envoie le message que vous voulez sur tous les salons puis les supprime";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();

        if (!event.isFromGuild()) {
            new EnvoiMessage().sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        if (event.getMember() == null) {
            return;
        }
        if (!event.getMember().getUser().getId().equals("264490592610942976")) {
            event.getChannel().sendMessage("Tu n'as pas la permission pour faire cette commande.").queue();
            return;
        }
        event.getMessage().delete().queue();
        Guild guild = event.getGuild();
        StringBuilder message = new StringBuilder();
        if (args.size() < 1) {
            message = new StringBuilder("Coucou");
        } else {
            for (String arg : args) {
                message.append(arg).append(" ");
            }
        }
        for (TextChannel c : guild.getTextChannels()) {
            MessageCreateAction action = c.sendMessage(message);
            action.flatMap(Message::delete).queue();
        }
    }
}
