package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.Commands.enums.Commands;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HelpCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "help";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"h", "aide", "a", "commandes", "commands", "c"};
    }

    @Override
    public String getDescription() {
        return "Permet de savoir comment utiliser les commandes.";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " [commande]";
    }

    private EmbedBuilder help(boolean n) {

        EmbedBuilder builder = new EmbedBuilder();
        Map<Category, List<Command>> commandss = Commands.getCommandsGroupedByCategory();
        if (!n) {
            commandss.remove(Category.N);
        }
        TreeMap<Category, List<Command>> commandsGroupedByCategory = new TreeMap<>(commandss);

        final String titre = "Liste des commandes de l'EdtBot";
        final String imageI = "https://image.flaticon.com/icons/png/512/1301/1301429.png";

        builder.setThumbnail(imageI);
        builder.setColor(getColor());
        builder.setTitle(titre, "https://www.remontees-mecaniques.net/");
        builder.appendDescription(getDescription());

        for (Map.Entry<Category, List<Command>> categoryListEntry : commandsGroupedByCategory.entrySet()) {
            Category category = categoryListEntry.getKey();
            List<Command> commands = categoryListEntry.getValue();

            String titreField = category.getName();
            String descriptionField = category.getDescription() + "\n" + commands.stream().map(cmd -> String.format("`%s`", cmd.getLabel())).collect(Collectors.joining(" "));
            builder.addField(titreField, descriptionField, false);

        }
        return builder;
    }

    private EmbedBuilder help(Command command, boolean n) {

        if (command == null || command.getCategory().equals(Category.N) && !n) {
            return null;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(command.getColor());
        builder.setTitle("Commande " + command.getLabel());
        builder.appendDescription(command.getDescription());

        builder.addField("Signature", "`" + command.getSignature() + "`", false);

        String[] alliass = command.getAlliass();
        if (alliass.length != 0) {
            builder.addField("Alias", Arrays.stream(alliass).map(al -> String.format("`%s`", al)).collect(Collectors.joining(" ")), false);
        }

        MessageEmbed.Field[] extras = command.getExtraFields();
        if (extras.length != 0) {
            Arrays.stream(extras).forEach(builder::addField);
        }

        return builder;
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        EmbedBuilder message;
        boolean n = false;
        if (event.isFromGuild()) {
            TextChannel channelTest = (TextChannel) event.getChannel();
            n = channelTest.isNSFW();
        }

        if (args.size() == 0) {
            message = this.help(n);
        } else {
            Command command = Commands.getCommand(args.get(0));
            message = this.help(command, n);
        }

        if (message == null) {
            EnvoiMessage.sendMessage(event, "La commande `" + args.get(0) + "` n'existe pas");
            return;
        }

        if (author != null)
            message.setFooter(author.getName(), author.getAvatarUrl());
        EnvoiMessage.sendMessage(event, message.build());
    }
}
