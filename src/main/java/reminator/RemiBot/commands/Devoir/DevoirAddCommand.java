package reminator.RemiBot.commands.Devoir;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.Devoir.Model.BDDevoir;
import reminator.RemiBot.commands.Devoir.Model.BDDevoirJson;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevoirAddCommand implements Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    @Override
    public Category getCategory() {
        return Category.DEVOIR;
    }

    @Override
    public String getLabel() {
        return "devoir-add";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"d-a", "da"};
    }

    @Override
    public String getDescription() {
        return "Ajoute un devoir à la base de devoirs";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " [mention] <matière> [<JJ/MM/AAAA>] <description>";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();

        if (args.size() < 2) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisée, voir `r!help devoir-add`");
            return;
        }
        System.out.println(args);
        String mention = args.get(0);

        ArrayList<String> ids = new ArrayList<>();
        boolean all = false;
        ArrayList<User> users = new ArrayList<>();

        String type;
        int indiceMatiere = 1;


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
            ids.add(String.valueOf(event.getAuthor().getIdLong()));
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

        String matiere = args.get(indiceMatiere);
        int indiceDescription = indiceMatiere + 2;

        Date date = null;

        pattern = Pattern.compile("([0-9][0-9])/([0-9][0-9])/([0-9][0-9][0-9][0-9])");
        matcher = pattern.matcher(args.get(indiceMatiere + 1));

        if (matcher.find()) {
            int jour = Integer.parseInt(matcher.group(1));
            int mois = Integer.parseInt(matcher.group(2));
            int annee = Integer.parseInt(matcher.group(3));

            date = new Date(annee, mois-1, jour);
        } else {
            indiceDescription--;
        }

        StringBuilder description = new StringBuilder();
        for (int i = indiceDescription; i < args.size(); i++) {
            description.append(args.get(i)).append(" ");
        }

        bdDevoir.addDevoir(users, matiere, description.toString(), all, date);
        EnvoiMessage.sendMessage(event, "devoir ajouté pour " + mention);
    }
}
