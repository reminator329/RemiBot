package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
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
            new EnvoiMessage().sendMessage(event, "Commande mal utilisée, voir `r!help play`");
            return;
        }

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

            AudioManager audioManager = guild.getAudioManager();
            AudioChannel audioChannel = voiceState.getChannel();

            audioManager.openAudioConnection(audioChannel);
            assert audioChannel != null;
            new EnvoiMessage().sendMessage(event, "Je suis maintenant connecté sur " + audioChannel.getAsMention());
        }

        String link = String.join(" ", args);

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
            System.out.println("teste");
        }

        PlayerManager.getInstance().loadAndPlay(textChannel, link, new TrackUserData().withRequestedUser(event.getAuthor()));
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
