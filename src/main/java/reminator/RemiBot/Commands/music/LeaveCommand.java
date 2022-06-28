package reminator.RemiBot.Commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.Commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.Commands.music.lavaplayer.TrackScheduler;
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
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        Member member = event.getMember();
        if (member == null) return;

        GuildVoiceState voiceState = member.getVoiceState();
        assert voiceState != null;
        if (!voiceState.inAudioChannel()) {
            EnvoiMessage.sendMessage(event, "Tu dois être dans un salon vocal pour utiliser cette commande.");
            return;
        }

        Guild guild = event.getGuild();
        Member selfMember = guild.getMemberById(RemiBot.api.getSelfUser().getId());
        assert selfMember != null;
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        assert selfVoiceState != null;
        if (!selfVoiceState.inAudioChannel()) {
            EnvoiMessage.sendMessage(event, "Je dois être dans un salon vocal pour jouer une musique.");
            return;
        }

        assert selfVoiceState.getChannel() != null;
        if (!selfVoiceState.getChannel().equals(voiceState.getChannel())) {
            EnvoiMessage.sendMessage(event, "Tu dois être dans le même salon vocal que moi.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.leave();

        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();
    }
}
