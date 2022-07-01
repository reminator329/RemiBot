package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class StopCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "stop";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"arret", "s"};
    }

    @Override
    public String getDescription() {
        return "Permet d'arrêter la musique et de vider la queue.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        if (!event.isFromGuild()) {
            new EnvoiMessage().sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        Member member = event.getMember();
        if (member == null) return;

        Guild guild = event.getGuild();
        Member selfMember = guild.getMemberById(RemiBot.api.getSelfUser().getId());
        assert selfMember != null;
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        assert selfVoiceState != null;
        if (!selfVoiceState.inAudioChannel()) {
            new EnvoiMessage().sendMessage(event, "Je dois être dans un salon vocal pour exécuter cette commande.");
            return;
        }

        GuildVoiceState voiceState = member.getVoiceState();
        assert voiceState != null;
        if (!voiceState.inAudioChannel()) {
            new EnvoiMessage().sendMessage(event, "Tu dois être dans le même salon vocal que moi.");
            return;
        }

        assert selfVoiceState.getChannel() != null;
        if (!voiceState.inAudioChannel() || !selfVoiceState.getChannel().equals(voiceState.getChannel())) {
            new EnvoiMessage().sendMessage(event, "Tu dois être dans le même salon vocal que moi.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.stop();

        new EnvoiMessage().sendMessage(event, "Musique arrêtée et queue vidée.");
    }
}
