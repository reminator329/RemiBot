package reminator.RemiBot.commands.birthday;

import net.dv8tion.jda.api.entities.Message;
import reminator.RemiBot.Services.birthday.BirthdayService;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class BirthdayCommand implements Command {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM");

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "anniversaire";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"anniv"};
    }

    @Override
    public String getDescription() {
        return "Enregistre l'anniversaire de quelqu'un. Utilisation : `r!anniversaire <nom> <date>`";
    }

    @Override
    public void execute(CommandExecutedEvent event) {
        Message message = event.getMessage();

        if (event.getArgs().size() != 2) {
            message.reply("Utilisation : `r!anniversaire <nom> <date>`").queue();
            return;
        }

        String name = event.getArgs().get(0);
        String rawDate = event.getArgs().get(1);

        TemporalAccessor date;
        try {
            date = FORMAT.parse(rawDate);
        } catch (Exception e) {
            message.reply("La date doit être au format `dd/MM`.\n" + e.getMessage()).queue();
            return;
        }

        Birthday birthday = new Birthday(date.get(ChronoField.DAY_OF_MONTH), Month.of(date.get(ChronoField.MONTH_OF_YEAR)), name, event.getAuthor().getId());
        boolean replaced = BirthdayService.get().hasBirthday(birthday);

        BirthdayService.get().addBirthday(birthday);

        if(replaced) {
            message.reply("Anniversaire de " + name + " mis à jour au " + rawDate).queue();
        } else {
            message.reply("Anniversaire de " + name + " ajouté au " + rawDate).queue();
        }
    }
}
