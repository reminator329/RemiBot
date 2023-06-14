package reminator.RemiBot.Services.reminder;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import reminator.RemiBot.commands.birthday.Birthday;

import java.time.LocalTime;
import java.util.*;

public class ReminderService {
    private static ReminderService INSTANCE;

    public static synchronized void init(TextChannel channel) {
        if(INSTANCE != null) {
            return;
        }
        INSTANCE = new ReminderService(channel);
    }

    public static ReminderService get() {
        return INSTANCE;
    }

    private List<Reminder> reminders = List.of(new SleepReminder(), new SleepReminder2());
    private final TextChannel channel;

    public ReminderService(TextChannel channel) {
        this.channel = channel;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000*60);
    }

    private void update() {
        LocalTime now = LocalTime.now();
        for(Reminder reminder : reminders) {
            if (reminder.getTime().getMinute() == now.getMinute() && reminder.getTime().getHour() == now.getHour()) {
                String message = pickRandom(reminder.getMessages());
                message += " <@" + reminder.getUserId() + ">";
                channel.sendMessage(message).queue();
            }
        }
    }

    private String pickRandom(List<String> messages) {
        return messages.get(new Random().nextInt(messages.size()));
    }
}
