package reminator.RemiBot.commands.admin;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveRoleCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.ADMIN;
    }

    @Override
    public String getLabel() {
        return "remove-role";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"r-r", "rr"};
    }

    @Override
    public String getDescription() {
        return "Permet de supprimer le.s rôle.s sur tout le serveur.";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + "< rôle>*";
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

        if (args.size() < 1) {
            new EnvoiMessage().sendMessage(event, "Commande mal utilisée, voir `r!help " + this.getLabel() + "`");
            return;
        }

        Guild guild = event.getGuild();

        for (String arg : args) {

            Pattern pattern = Pattern.compile("<@&([0-9]+)>");
            Matcher matcher = pattern.matcher(arg);

            if (matcher.find()) {
                String idRole = matcher.group(1);
                Role role = guild.getRoleById(idRole);
                System.out.println(role);
                if (role == null) continue;

                guild.pruneMemberCache();
                List<Member> members = guild.getMembersWithRoles(role);
                System.out.println(members);

                members.forEach(m -> {
                    guild.removeRoleFromMember(m, role).queue();
                    new EnvoiMessage().sendMessage(event, "Le rôle " + role.getAsMention() + " a été retiré pour " + m.getAsMention() + " (" + m.getEffectiveName() + ").");
                });
            }
        }
    }
}
