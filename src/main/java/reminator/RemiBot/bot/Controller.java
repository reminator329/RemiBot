package reminator.RemiBot.bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.*;
import reminator.RemiBot.Commands.*;

import java.util.ArrayList;

public class Controller extends ListenerAdapter {

    private final ArrayList<Command> commands = new ArrayList<>();
    private final PingCommand pingCommand;
    private final AlbumCommand albumCommand;
    private final EcouteBilalCommand ecouteBilalCommand;
    private final SpamCommand spamCommand;
    private final HelpCommand helpCommand;
    private final BilalCommand bilalCommand;
    private final YoutubeurCommand youtubeurCommand;
    private final DevinetteCommand devinetteCommand;
    private final AmongusCommand amongusCommand;
    private final PlusMoinsCommand plusMoinsCommand;
    private final ProchainCoursCommand prochainCoursCommand;

    private final ArrayList<Categorie> categories = new ArrayList<>();
    private final BilalCategorie bilalCategorie = new BilalCategorie();
    private final AutresCategorie autresCategorie = new AutresCategorie();
    private final JeuCategorie jeuCategorie = new JeuCategorie();
    private final EdtCategorie edtCategorie = new EdtCategorie();

    public Controller() {
        // Catégories
        categories.add(bilalCategorie);
        categories.add(edtCategorie);
        categories.add(jeuCategorie);
        categories.add(autresCategorie);

        // Commandes
        pingCommand = new PingCommand();
        albumCommand = new AlbumCommand();
        ecouteBilalCommand = new EcouteBilalCommand();
        spamCommand = new SpamCommand();
        helpCommand = new HelpCommand(this);
        bilalCommand = new BilalCommand();
        youtubeurCommand = new YoutubeurCommand();
        devinetteCommand = new DevinetteCommand();
        amongusCommand = new AmongusCommand();
        plusMoinsCommand = new PlusMoinsCommand();
        prochainCoursCommand = new ProchainCoursCommand();

        // Ajout de la commande dans la liste
        commands.add(pingCommand);
        commands.add(albumCommand);
        commands.add(ecouteBilalCommand);
        commands.add(spamCommand);
        commands.add(helpCommand);
        commands.add(bilalCommand);
        commands.add(youtubeurCommand);
        commands.add(devinetteCommand);
        commands.add(amongusCommand);
        commands.add(plusMoinsCommand);
        commands.add(prochainCoursCommand);

        // Ajout de la commande dans la catégorie
        bilalCategorie.addCommand(ecouteBilalCommand);
        bilalCategorie.addCommand(albumCommand);
        bilalCategorie.addCommand(bilalCommand);
        bilalCategorie.addCommand(devinetteCommand);

        autresCategorie.addCommand(pingCommand);
        autresCategorie.addCommand(spamCommand);
        autresCategorie.addCommand(helpCommand);
        autresCategorie.addCommand(youtubeurCommand);
        autresCategorie.addCommand(amongusCommand);

        jeuCategorie.addCommand(plusMoinsCommand);

        edtCategorie.addCommand(prochainCoursCommand);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        /*
        int n = 1 + (int)(Math.random() * ((10 - 1) + 1));
        if (n == 5) {
            for (Member m : event.getGuild().getMembers()) {
                if (m.getUser().getAsTag().equalsIgnoreCase("Elorya#5162")) {
                    event.getChannel().sendMessage(m.getUser().getAsMention()).queue();
                }
            }
        }
         */

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        for (Command c : commands) {
            String prefixLabel = c.getPrefix() + c.getLabel();
            if (prefixLabel.equalsIgnoreCase(args[0])) {
                c.executerCommande(event);
            }
        }
    }

    public ArrayList<Categorie> getCategories() {
        return new ArrayList<>(this.categories);
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(this.commands);
    }
}