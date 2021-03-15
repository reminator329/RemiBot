package reminator.RemiBot.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.*;
import reminator.RemiBot.Commands.*;
import reminator.RemiBot.Commands.Devoir.DevoirAddCommand;
import reminator.RemiBot.Commands.Devoir.DevoirCommand;
import reminator.RemiBot.Commands.Devoir.DevoirFiniCommand;
import reminator.RemiBot.Commands.nsfw.NSFWCategoriesCommand;
import reminator.RemiBot.Commands.nsfw.NSFWCommand;
import reminator.RemiBot.Commands.nsfw.NSFWUpdateCommand;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.TimeZone;

public class Controller extends ListenerAdapter {

    private final ArrayList<Message> messages = new ArrayList<>();
    private final int nbMessageMax = 5000;

    private final ArrayList<Command> commands = new ArrayList<>();
    private final PingCommand pingCommand;
    private final PongCommand pongCommand;
    //private final AlbumCommand albumCommand;
    private final EcouteBilalCommand ecouteBilalCommand;
    private final SpamCommand spamCommand;
    private final HelpCommand helpCommand;
    private final BilalCommand bilalCommand;
    private final YoutubeurCommand youtubeurCommand;
    private final DevinetteCommand devinetteCommand;
    private final AmongusCommand amongusCommand;
    private final PlusMoinsCommand plusMoinsCommand;
    private final JeuxMultiCommand jeuxMultiCommand;
    private final PollCommand pollCommand;
    private final DevoirCommand devoirCommand;
    private final DevoirAddCommand devoirAddCommand;
    private final DevoirFiniCommand devoirFiniCommand;
    private final GhostPingCommand ghostPingCommand;
    private final NSFWCommand nsfwCommand;
    private final NSFWUpdateCommand nsfwUpdateCommand;
    private final NSFWCategoriesCommand nsfwCategoriesCommand;

    private final ArrayList<Categorie> categories = new ArrayList<>();
    private final BilalCategorie bilalCategorie = new BilalCategorie();
    private final AutresCategorie autresCategorie = new AutresCategorie();
    private final JeuCategorie jeuCategorie = new JeuCategorie();
    private final DevoirCategorie devoirCategorie = new DevoirCategorie();
    private final NsfwCategorie nsfwCategorie = new NsfwCategorie();


    public Controller() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));

        // Catégories
        categories.add(bilalCategorie);
        categories.add(jeuCategorie);
        categories.add(autresCategorie);
        categories.add(devoirCategorie);
        categories.add(nsfwCategorie);

        // Commandes
        pingCommand = new PingCommand();
        pongCommand = new PongCommand();
        //albumCommand = new AlbumCommand();
        ecouteBilalCommand = new EcouteBilalCommand();
        spamCommand = new SpamCommand();
        helpCommand = new HelpCommand(this);
        bilalCommand = new BilalCommand();
        youtubeurCommand = new YoutubeurCommand();
        devinetteCommand = new DevinetteCommand();
        amongusCommand = new AmongusCommand();
        plusMoinsCommand = new PlusMoinsCommand();
        jeuxMultiCommand = new JeuxMultiCommand();
        pollCommand = new PollCommand();
        devoirCommand = new DevoirCommand();
        devoirAddCommand = new DevoirAddCommand();
        devoirFiniCommand = new DevoirFiniCommand();
        ghostPingCommand = new GhostPingCommand();

        nsfwCommand = new NSFWCommand();
        nsfwUpdateCommand = new NSFWUpdateCommand();
        nsfwCategoriesCommand = new NSFWCategoriesCommand();

        // Ajout de la commande dans la liste
        commands.add(pingCommand);
        commands.add(pongCommand);
        //commands.add(albumCommand);
        commands.add(ecouteBilalCommand);
        commands.add(spamCommand);
        commands.add(helpCommand);
        commands.add(bilalCommand);
        commands.add(youtubeurCommand);
        commands.add(devinetteCommand);
        commands.add(amongusCommand);
        commands.add(plusMoinsCommand);
        commands.add(jeuxMultiCommand);
        commands.add(pollCommand);
        commands.add(devoirCommand);
        commands.add(devoirAddCommand);
        commands.add(devoirFiniCommand);
        commands.add(ghostPingCommand);
        commands.add(nsfwCommand);
        commands.add(nsfwUpdateCommand);
        commands.add(nsfwCategoriesCommand);

        // Ajout de la commande dans la catégorie
        bilalCategorie.addCommand(ecouteBilalCommand);
        //bilalCategorie.addCommand(albumCommand);
        bilalCategorie.addCommand(bilalCommand);
        bilalCategorie.addCommand(devinetteCommand);

        autresCategorie.addCommand(pingCommand);
        autresCategorie.addCommand(pongCommand);
        autresCategorie.addCommand(spamCommand);
        autresCategorie.addCommand(helpCommand);
        autresCategorie.addCommand(youtubeurCommand);
        autresCategorie.addCommand(amongusCommand);
        autresCategorie.addCommand(pollCommand);
        autresCategorie.addCommand(ghostPingCommand);

        jeuCategorie.addCommand(plusMoinsCommand);
        jeuCategorie.addCommand(jeuxMultiCommand);

        devoirCategorie.addCommand(devoirCommand);
        devoirCategorie.addCommand(devoirAddCommand);
        devoirCategorie.addCommand(devoirFiniCommand);

        nsfwCategorie.addCommand(nsfwCommand);
        nsfwCategorie.addCommand(nsfwUpdateCommand);
        nsfwCategorie.addCommand(nsfwCategoriesCommand);
    }

    Guild guild;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isFromGuild()) return;

        Member remi = guild.getMemberById("368733622246834188");
        if (remi == null) {
            return;
        }

        remi.getUser().openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(event.getAuthor().getName() + " m'a écrit " + event.getMessage().getContentDisplay()))
        .queue();

        event.getAuthor().openPrivateChannel()
                .flatMap(channel -> channel.sendMessage("3"))
                .delay(Duration.ofSeconds(1))
                .flatMap(channel -> channel.editMessage("2"))
                .delay(Duration.ofSeconds(1))
                .flatMap(channel -> channel.editMessage("1"))
                .delay(Duration.ofSeconds(1))
                .flatMap(Message::delete)
                .queue();
    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        if (event.getAuthor().isBot()) return;
        Guild guild = event.getGuild();
        Member remi = guild.getMemberById("368733622246834188");

        Message newMessage = event.getMessage();
        Message ancienMessage = null;

        for (Message m : this.messages) {
            if (m.getId().equals(newMessage.getId())) {
                this.messages.remove(m);
                ancienMessage = m;
                break;
            }
        }
        if (this.messages.size() >= this.nbMessageMax) {
            this.messages.remove(0);
        }
        this.messages.add(newMessage);
        if (ancienMessage == null) {
            return;
        }
        if (remi == null) {
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Modification de message (" + guild.getName() + ")", newMessage.getJumpUrl());
        embedBuilder.appendDescription(newMessage.getJumpUrl() + "\n" + ancienMessage.getAuthor().getName());
        embedBuilder.addField("Ancien message", ancienMessage.getContentDisplay(), false);
        embedBuilder.addField("Nouveau message", newMessage.getContentDisplay(), false);
        embedBuilder.setFooter(event.getAuthor().getName(), event.getAuthor().getAvatarUrl());

        remi.getUser().openPrivateChannel()
                .flatMap(privateChannel -> privateChannel
                        .sendMessage(embedBuilder.build()))
                .queue();
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {
        Guild guild = event.getGuild();
        Member remi = guild.getMemberById("368733622246834188");

        String messageId = event.getMessageId();
        Message ancienMessage = null;

        for (Message m : this.messages) {
            if (m.getId().equals(messageId)) {
                this.messages.remove(m);
                ancienMessage = m;
                break;
            }
        }
        if (ancienMessage == null) {
            return;
        }
        if (remi == null) {
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Supression de message (" + guild.getName() + ")");
        embedBuilder.appendDescription(event.getChannel().getAsMention() + "\n" + ancienMessage.getAuthor().getName());
        embedBuilder.addField("Ancien message", ancienMessage.getContentDisplay(), false);
        embedBuilder.setFooter(ancienMessage.getAuthor().getName(), ancienMessage.getAuthor().getAvatarUrl());

        remi.getUser().openPrivateChannel()
                .flatMap(privateChannel -> privateChannel
                        .sendMessage(embedBuilder.build()))
                .queue();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        this.guild = event.getGuild();
        if (this.messages.size() >= this.nbMessageMax) {
            this.messages.remove(0);
        }
        this.messages.add(event.getMessage());
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
            String[] test = args[0].split(c.getPrefix());
            if (prefixLabel.equalsIgnoreCase(args[0]) || test.length > 1 && c.isAlias(test[1])) {
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