package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.utils.EnvoiMessage;

public abstract class Button extends ButtonImpl {

    private final String id;
    private final String label;
    private final EmojiUnion emoji;

    public Button(String id, String label, UnicodeEmoji emoji) {
        super(id, label, ButtonStyle.SECONDARY, false, emoji);
        this.id = id;
        this.label = label;
        this.emoji = (EmojiUnion) emoji;
    }

    public String getLabel() {
        return label;
    }

    public EmojiUnion getEmoji() {
        return emoji;
    }

    public String getId() {
        return id;
    }

    public void onClick(@NotNull ButtonInteractionEvent event) {
        new EnvoiMessage().sendGuild(event.getChannel(), "Ce bouton est en cours de développement.");
    }
}
