package reminator.RemiBot.Services.birthday;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import reminator.RemiBot.commands.birthday.Birthday;

import java.io.*;
import java.util.*;

public class BirthdayService {
    private static BirthdayService INSTANCE;

    public static synchronized void init(TextChannel channel) {
        if(INSTANCE != null) {
            return;
        }
        INSTANCE = new BirthdayService(channel);
    }

    public static BirthdayService get() {
        return INSTANCE;
    }

    private static final File FILE = new File("data/birthdays.bin");

    private final TextChannel channel;
    private final Set<Birthday> birthdays = new HashSet<>();

    public BirthdayService(TextChannel channel) {
        this.channel = channel;
        if (FILE.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
                this.birthdays.addAll((Collection<Birthday>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            FILE.getParentFile().mkdirs();
        }

        System.out.println("Loaded birthdays: ");
        System.out.println(birthdays);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                check();
            }
        }, startDate(), 1000*60*60*24);
    }

    private void check() {
        for (Birthday birthday : birthdays) {
            if (birthday.isToday()) {
                channel.sendMessage("C'est l'anniversaire de " + birthday.name() + " aujourd'hui ! :partying_face: <@"+birthday.authorId()+">").queue();
            }
        }
    }

    public void addBirthday(Birthday birthday) {
        birthdays.remove(birthday);
        birthdays.add(birthday);
        save();
    }

    public boolean hasBirthday(Birthday birthday) {
        return birthdays.contains(birthday);
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(birthdays);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Date startDate(){
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, 1);
        Calendar result = new GregorianCalendar(
                tomorrow.get(Calendar.YEAR),
                tomorrow.get(Calendar.MONTH),
                tomorrow.get(Calendar.DATE),
                8,
                0
        );
        return result.getTime();
    }
}
