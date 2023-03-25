package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
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
        if (selfVoiceState.inAudioChannel()) {
            new EnvoiMessage().sendMessage(event, "Je suis déjà dans un channel vocal.");
            return;
        }

        AudioManager audioManager = guild.getAudioManager();
        AudioChannel audioChannel = voiceState.getChannel();

        audioManager.openAudioConnection(audioChannel);
        assert audioChannel != null;
        new EnvoiMessage().sendMessage(event, "Je suis maintenant connecté sur " + audioChannel.getAsMention());
    }
}
