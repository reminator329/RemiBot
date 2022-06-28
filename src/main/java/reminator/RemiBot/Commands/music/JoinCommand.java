package reminator.RemiBot.Commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class JoinCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "join";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"j"};
    }

    @Override
    public String getDescription() {
        return "Permet de faire venir RémiBot dans son salon vocal.";
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
        if (selfVoiceState.inAudioChannel()) {
            EnvoiMessage.sendMessage(event, "Je suis déjà dans un channel vocal.");
            return;
        }

        AudioManager audioManager = guild.getAudioManager();
        AudioChannel audioChannel = voiceState.getChannel();

        audioManager.openAudioConnection(audioChannel);
        assert audioChannel != null;
        EnvoiMessage.sendMessage(event, "Je suis maintenant connecté sur " + audioChannel.getAsMention());
    }
}
