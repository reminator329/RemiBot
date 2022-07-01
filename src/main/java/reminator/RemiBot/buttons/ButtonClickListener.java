package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.utils.EnvoiMessage;

public class ButtonClickListener extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (!event.isFromGuild()) {
            new EnvoiMessage().sendPrivate(event.getUser(), "Tu ne peux pas cliquer sur ce bouton en privé.");
            return;
        }

        event.deferEdit().queue();

        System.out.println("Bouton");

        net.dv8tion.jda.api.interactions.components.Button eventButton = event.getButton();
        if (eventButton == null) {
            return;
        }

        Button button = Buttons.getButtonById(eventButton.getId());
        if (button == null) {
            return;
        }
        button.onClick(event);
    }
}
