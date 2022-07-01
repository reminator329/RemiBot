package reminator.RemiBot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlbumCommand implements Command {

    // year : année - 1900 ; mouth : Calendar.MOUTH ; date/h/m : date - 1H
    private static final Date prochainAlbum = new Date(2020, Calendar.NOVEMBER, 6);

    @Override
    public Category getCategory() {
        return Category.BILAL;
    }

    @Override
    public String getLabel() {
        return "album";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Donne le temps avant la sortie du prochain album de Bilal !";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        Date date = new Date();
        String message;
        if (date.compareTo(prochainAlbum) < 0) {
            message = new SimpleDateFormat("d 'jours', h 'heures', m 'minutes', s 'seconds', S 'millisecondes'")
                    .format(new Date(prochainAlbum.getTime()-date.getTime() - 2000*3600));

        } else {
            message = "Pas de date pour le moment :/";
        }
        new EnvoiMessage().sendMessage(event, message);
    }
}
