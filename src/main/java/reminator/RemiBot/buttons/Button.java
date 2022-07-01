package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.interactions.ButtonImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reminator.RemiBot.utils.EnvoiMessage;

public abstract class Button extends ButtonImpl {

    private final String id;
    private final String label;
    private final Emoji emoji;

    public Button(String id, String label, Emoji emoji) {
        super(id, label, ButtonStyle.SECONDARY, false, emoji);
        this.id = id;
        this.label = label;
        this.emoji = emoji;
    }

    public String getLabel() {
        return label;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public String getId() {
        return id;
    }

    public void onClick(@NotNull ButtonClickEvent event) {
        new EnvoiMessage().sendGuild(event.getChannel(), "Ce bouton est en cours de développement.");
    }
}
