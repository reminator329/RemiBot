package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.ArrayList;

public class HelpCommand extends Command {

    private final Commands commands;

    public HelpCommand(Commands commands) {
        this.commands = commands;

        this.setPrefix(RemiBot.prefix);
        this.setLabel("help");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();

        final String titre = "Liste des commandes du RémiBot !";
        final String imageI = "https://image.flaticon.com/icons/png/512/1301/1301429.png";

        builder.setThumbnail(imageI);
        builder.setColor(Color.RED);
        builder.setTitle(titre, "https://www.remontees-mecaniques.net/");
        builder.appendDescription("Utilise `r!help <commande>` pour plus d'informations sur une commande.");

        builder.addField("Bilal", "Les commandes liées à Bilal.\n`album` `musique`", false);
        builder.addField("Autres", "Les commandes autres.\n`ping` `elorya`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        channel.sendTyping().queue();

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Member member = event.getMember();

        MessageEmbed message = null;
        if (args.length == 1) {
            if (member != null) {
                EmbedBuilder preMessage = new EmbedBuilder(this.getHelp());
                preMessage.setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());
                message = preMessage.build();
            } else {
                message = this.getHelp();
            }
            channel.sendMessage(message).queue();
        } else {
            ArrayList<Command> commands = this.commands.getCommands();

            for (Command c : commands) {
                if (c.getLabel().equalsIgnoreCase(args[1])) {
                    if (member != null) {
                        EmbedBuilder preMessage = new EmbedBuilder(c.getHelp());
                        preMessage.setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());
                        message = preMessage.build();
                    } else {
                        message = c.getHelp();
                    }
                    channel.sendMessage(message).queue();
                }
            }
            if (message == null) {
                channel.sendMessage("La commande `" + args[1] + "` n'existe pas").queue();
            }
        }
    }
}
