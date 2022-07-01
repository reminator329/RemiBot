package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ButtonClickListener extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        event.deferEdit().queue();

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
