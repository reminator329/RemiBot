package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.utils.EnvoiMessage;

import javax.annotation.Nonnull;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public class InconnuCommand implements Command {

    private final static byte[] SALT = {-121, -63, -7, -17, 123, 122, -100, 20, -111, -28, -114, -116, -120, 73, -91, 50};
    private final static Set<String> HASHES = Set.of("OIw8LXQyWDB87lSXl8paNg==", "EGucGvg9ZTLTg9BlYb1I4A==");

    public static String hash(String input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(input.toCharArray(), SALT, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "inconnu";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"i"};
    }

    @Override
    public String getDescription() {
        return "Devine qui est cette personne :face_with_monocle:";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }

        final long authorId = event.getAuthor().getIdLong();
        final long channelId = channel.getIdLong();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setImage("https://cdn.discordapp.com/attachments/685895494387499068/835216207799779328/devine-qui-c-est.jpg")
                .setDescription("Qui est cette personne ? :face_with_monocle:");
        channel.sendMessage(embed.build()).queue();

        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;

                String hashedInput = null;
                try {
                    hashedInput = hash(event.getMessage().getContentRaw().toLowerCase());
                } catch (Exception ignored) {
                }

                String feedback = HASHES.contains(hashedInput)
                        ? "Félicitations !!!! Tu as deviné la personne ! :partying_face:"
                        : "Euuuh non c'est pas ça ! Essaie encore ! :slight_smile:";

                channel.sendMessage(new MessageBuilder().append(feedback).build()).queue();

                event.getJDA().removeEventListener(this);
            }
        });
    }
}
