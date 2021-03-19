package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevoirAddCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirAddCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir-add");
        this.addAlias("d-a");
        this.addAlias("da");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devoir-add");
        builder.appendDescription("Ajoute un devoir à la base de devoirs");
        builder.addField("Signature", "`r!devoir-add [mention] <matière> <description>`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args.length < 3) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisée, voir `r!help devoir-add`");
            return;
        }
        String mention = args[1];

        ArrayList<String> ids = new ArrayList<>();
        boolean all = false;
        ArrayList<User> users = new ArrayList<>();

        String type;
        int indiceMatiere = 2;


        if (mention.contains("!")) {
            type = "!";
        } else if (mention.contains("&")) {
            type = "&";
        } else if (mention.equals("@everyone")) {
            type = "@everyone";
        } else {
            type = "";
        }

        Pattern pattern = Pattern.compile("<@" + type + "([0-9]+)>");
        Matcher matcher = pattern.matcher(mention);

        if (matcher.find()) {
            switch (type) {
                case "&" -> {
                    if (event.isFromGuild()) {
                        Guild server = event.getGuild();
                        for (Member member : server.getMembersWithRoles(server.getRoleById(matcher.group(1)))) {
                            users.add(member.getUser());
                        }
                    } else {
                        EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
                        return;
                    }
                }
                case "!", "" -> ids.add(matcher.group(1));
            }
        } else if (type.equalsIgnoreCase("@everyone")) {

            if (event.isFromGuild()) {
                Guild server = event.getGuild();
                for (Member m : server.getMembers()) {
                    ids.add(m.getUser().getId());
                }
            } else {
                EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
                return;
            }
            all = true;
        } else {
            users.add(event.getAuthor());
            mention = "<@!" + event.getAuthor().getIdLong() + '>';
            indiceMatiere--;
        }

        if (event.isFromGuild()) {
            Guild server = event.getGuild();
            for (String id : ids) {
                Member member = server.getMemberById(id);
                if (member == null) {
                    return;
                }
                users.add(member.getUser());
            }
        } else {
            users.add(event.getAuthor());
        }

        String matiere = args[indiceMatiere];
        StringBuilder description = new StringBuilder();
        for (int i = indiceMatiere + 1; i < args.length; i++) {
            description.append(args[i]).append(" ");
        }

        bdDevoir.addDevoir(users, matiere, description.toString(), all);
        EnvoiMessage.sendMessage(event, "devoir ajouté pour " + mention);
    }
}
