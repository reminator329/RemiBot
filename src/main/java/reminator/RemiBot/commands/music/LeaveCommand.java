package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class LeaveCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "leave";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"degage", "dégage"};
    }

    @Override
    public String getDescription() {
        return "Permet de me faire quitter le salon vocal dans lequel je suis.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        if (!event.isFromGuild()) {
            new EnvoiMessage().sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        Member member = event.getMember();
        if (member == null) return;

        GuildVoiceState voiceState = member.getVoiceState();
        assert voiceState != null;
        if (!voiceState.inAudioChannel()) {
            new EnvoiMessage().sendMessage(event, "Tu dois être dans un salon vocal pour utiliser cette commande.");
            return;
        }

        Guild guild = event.getGuild();
        Member selfMember = guild.getMemberById(RemiBot.api.getSelfUser().getId());
        assert selfMember != null;
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        assert selfVoiceState != null;
        if (!selfVoiceState.inAudioChannel()) {
            new EnvoiMessage().sendMessage(event, "Je dois être dans un salon vocal pour exécuter cette commande.");
            return;
        }

        assert selfVoiceState.getChannel() != null;
        if (!selfVoiceState.getChannel().equals(voiceState.getChannel())) {
            new EnvoiMessage().sendMessage(event, "Tu dois être dans le même salon vocal que moi.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.stop();

        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();

        new EnvoiMessage().sendMessage(event, "Merci d'avoir écouté ma musique, à bientôt !");
    }
}
