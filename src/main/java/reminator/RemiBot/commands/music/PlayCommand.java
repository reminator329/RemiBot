package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.utils.EnvoiMessage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements Command {
    @Override
    public Category getCategory() {
        return Category.MUSIQUE;
    }

    @Override
    public String getLabel() {
        return "play";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"p"};
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + " <Youtube URL>";
    }

    @Override
    public String getDescription() {
        return "Permet de jouer une musique dans le salon vocal dans lequel je me trouve.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();
        TextChannel textChannel = event.getTextChannel();

        if (args.size() < 1) {
            EnvoiMessage.sendMessage(event, "Commande mal utilisée, voir `r!help play`");
            return;
        }

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

        String link = String.join(" ", args);

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
            System.out.println("teste");
        }

        PlayerManager.getInstance().loadAndPlay(textChannel, link);
    }

    private boolean isUrl(String link) {
        try {
            new URL(link);
            System.out.println("url");
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
