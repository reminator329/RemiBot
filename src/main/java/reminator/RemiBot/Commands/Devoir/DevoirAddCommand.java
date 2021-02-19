package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirArray;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevoirAddCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirAddCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir-add");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devoir");
        builder.appendDescription("Ajoute un devoir à la base de devoirs");
        builder.addField("Signature", "`r!devoir-add <mention> <matière> <description>`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length < 4) {
            channel.sendMessage("Commande mal utilisé, voir `r!help devoir-add`").queue();
            return;
        }
        StringBuilder description = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            description.append(args[i]).append(" ");
        }
        String mention = args[1];
        Guild server = event.getGuild();

        ArrayList<String> ids = new ArrayList<>();
        boolean all = false;
        ArrayList<User> users = new ArrayList<>();
        System.out.println(mention);
        if (mention.contains("!")) {
            Pattern pattern = Pattern.compile("<@!([0-9]+)>");
            Matcher matcher = pattern.matcher(mention);
            if (matcher.find()) {
                ids.add(matcher.group(1));
            } else {
                channel.sendMessage("Commande mal utilisé, voir `r!help devoir-add`").queue();
                return;
            }

            for (String id : ids) {
                Member member = server.getMemberById(id);
                if (member == null) {
                    return;
                }
                users.add(member.getUser());
            }
        } else if (mention.contains("&")) {
            Pattern pattern = Pattern.compile("<@&([0-9]+)>");
            Matcher matcher = pattern.matcher(mention);
            if (matcher.find()) {
                for (Member member : server.getMembersWithRoles(server.getRoleById(matcher.group(1)))) {
                    users.add(member.getUser());
                }
            } else {
                channel.sendMessage("Commande mal utilisé, voir `r!help devoir-add`").queue();
                return;
            }
        } else if (mention.equals("@everyone")) {
            for (Member m : server.getMembers()) {
                ids.add(m.getUser().getId());
            }
            all = true;

            for (String id : ids) {
                Member member = server.getMemberById(id);
                if (member == null) {
                    return;
                }
                users.add(member.getUser());
            }
        } else {
            Pattern pattern = Pattern.compile("<@([0-9]+)>");
            Matcher matcher = pattern.matcher(mention);
            if (matcher.find()) {
                ids.add(matcher.group(1));
            } else {
                channel.sendMessage("Commande mal utilisé, voir `r!help devoir-add`").queue();
                return;
            }

            for (String id : ids) {
                Member member = server.getMemberById(id);
                if (member == null) {
                    return;
                }
                users.add(member.getUser());
            }
        }
        bdDevoir.addDevoir(users, args[2], description.toString(), all);
        channel.sendMessage("devoir ajouté pour " + args[1]).queue();
    }
}
