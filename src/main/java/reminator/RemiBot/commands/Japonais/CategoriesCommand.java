package reminator.RemiBot.commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.Japonais.model.Categorie;
import reminator.RemiBot.commands.Japonais.model.VocabulaireParserCSV;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.bot.BotEmbed;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;

public class CategoriesCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.JAPONAIS;
    }

    @Override
    public String getLabel() {
        return "vocabulaire-categories";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"vc"};
    }

    @Override
    public String getDescription() {
        return "Affiche les catégories de vocabulaire disponibles.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        EmbedBuilder builder = BotEmbed.BASE_USER(Objects.requireNonNull(event.getMember()).getUser());
        builder.setTitle("Catégories disponibles");

        Set<Categorie> categoriesSet = VocabulaireParserCSV.getInstance().update().getCategories();
        List<Categorie> categories = new ArrayList<>(categoriesSet);
        Collections.sort(categories);

        for (Categorie categorie : categories) {
            builder.addField(categorie.getNom(), "", false);
        }

        EnvoiMessage.sendMessage(event, builder.build());
    }
}
