package reminator.RemiBot.bot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class RemiBot {

    public static String prefix = "r!";
    public static String token;

    public static void main(String[] arguments) throws Exception {
        token = "NzY0OTM3NTQ1NzE1OTQxNDA2.X4Nhmg._tM_ZoVvPjkBWzVvFc7kdfosswg";
        JDA api = new JDABuilder(AccountType.BOT).setToken(token).build();
        api.addEventListener(new Controller());
        api.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("r!help"));
    }
}
