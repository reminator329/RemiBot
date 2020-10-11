package reminator.RemiBot.bot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import reminator.RemiBot.Commands.Commands;

public class RemiBot {

    public static String prefix = "r!";
    public static String token = "NzY0MjI3MDE0MjQ0MzAyODc4.X4DL3g.0kYjyw0BfvJ1MgXHvYNLs-8MDBw";

    public static void main(String[] arguments) throws Exception {
        JDA api = new JDABuilder(AccountType.BOT).setToken(token).build();
        api.addEventListener(new Commands());
        api.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("r!help"));
    }

}
