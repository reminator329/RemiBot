package reminator.RemiBot.commands.music;

import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class EcouteMusiqueCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "ecoute-musique";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"em", "e-m", "ecoute-music", "ecoute-m"};
    }

    @Override
    public String getDescription() {
        return "Permet d'envoyer les informations d'une musique automatiquement au lancement de celle-ci dans le salon actuel.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        musicManager.setDisplayChannel(event.getChannel());

        EnvoiMessage.sendMessage(event, "Le détail des musiques jouées sur ce serveur sera affiché dans " + event.getChannel().getAsMention());
        
    }
}
