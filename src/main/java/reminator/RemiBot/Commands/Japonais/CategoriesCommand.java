package reminator.RemiBot.Commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.Japonais.model.BDVocabulaire;
import reminator.RemiBot.Commands.Japonais.model.Categorie;
import reminator.RemiBot.Commands.Japonais.model.VocabulaireParserCSV;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.bot.BotEmbed;
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
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        EmbedBuilder builder = BotEmbed.BASE_USER.getBuilder(Objects.requireNonNull(event.getMember()).getUser());
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
