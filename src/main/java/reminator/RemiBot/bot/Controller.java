package reminator.RemiBot.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Commands;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller extends ListenerAdapter {

    JDA api;
    User user;

    public Controller(JDA api) {
        this.api = api;
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (!event.getName().equals("pingremi")) return;
        long time = System.currentTimeMillis();
        OptionMapping optEphemeral = event.getOption("ephemeral");
        boolean ephemeral;
        if (optEphemeral == null) {
            ephemeral = false;
        } else {
            ephemeral = optEphemeral.getAsBoolean();
        }
        event.reply("Pong!").setEphemeral(ephemeral)
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time)
                ).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().length() == 0) return;
        // event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField("field", "zoiegnzojeg", false).build()).setActionRow(Button.danger("id", "labe")).queue();
        /*
        if (Objects.equals(api.getUserById(264490592610942976L), event.getAuthor())) {
            String message = event.getMessage().getContentRaw();
            System.out.println(message);
            System.out.println(event.getMessage().getContentDisplay());

            Pattern pattern = Pattern.compile("<:.+:([0-9]+)>");
            Matcher matcher = pattern.matcher(message);
            //if (!matcher.find()) return;
            matcher.find();

            String idEmote = matcher.group(1);
            System.out.println(idEmote);
            Emote emote = api.getEmoteById(idEmote);
            event.getChannel().sendMessage(emote.getAsMention()).queue();
        }

         */
        List<String> args = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split("\\s+")));
        String command = args.get(0);


        for (Commands c : Commands.values()) {
            Command cmd = c.getCommand();
            String prefix = cmd.getPrefix();
            String label = cmd.getLabel();
            String prefixLabel = prefix + label;

            String[] separation = command.split("(?i)" + prefix);

            if (prefixLabel.equalsIgnoreCase(command) || separation.length > 1 && cmd.isAlias(separation[1])) {
                args.remove(0);
                c.getCommand().execute(new CommandExecutedEvent(event, args));
                break;
            }
        }

        user = api.getUserById(264490592610942976L);
        if (event.isFromGuild()) {
            return;
        }

        if (user == null) {
            return;
        }

        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(event.getAuthor().getName() + " - "  + event.getAuthor().getIdLong() + " - " + event.getMessage().getContentDisplay()))
        .queue();

        if (event.getAuthor().getIdLong() == 264490592610942976L) {

            Pattern pattern = Pattern.compile("([0-9]+)");
            Matcher matcher = pattern.matcher(args.get(0));

            if (matcher.find()) {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.size(); i++) {
                    message.append(args.get(i)).append(" ");
                }

                Objects.requireNonNull(api.getUserById(Long.parseLong(matcher.group()))).openPrivateChannel()
                        .flatMap(channel -> channel.sendMessage(message.toString()))
                        .queue();
            }
        }

/*
        event.getAuthor().openPrivateChannel()
                .flatMap(channel -> channel.sendMessage("3"))
                .delay(Duration.ofSeconds(1))
                .flatMap(channel -> channel.editMessage("2"))
                .delay(Duration.ofSeconds(1))
                .flatMap(channel -> channel.editMessage("1"))
                .delay(Duration.ofSeconds(1))
                .flatMap(Message::delete)
                .queue();

 */
    }

    /*
    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {

        if (event.getAuthor().isBot()) return;
        Guild guild = event.getGuild();
        Member remi = guild.getMemberById("264490592610942976");

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
        Member remi = guild.getMemberById("264490592610942976");

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
*/

    /*
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
        }
        /*
        BDDevoir bdDevoir = BDDevoirJson.getInstance();
        bdDevoir.ajoutTimer(new Eleve(event.getAuthor()));
*/

        /*
        this.guild = event.getGuild();
        if (this.messages.size() >= this.nbMessageMax) {
            this.messages.remove(0);
        }
        this.messages.add(event.getMessage());
        int n = 1 + (int)(Math.random() * ((10 - 1) + 1));
        if (n == 5) {
            for (Member m : event.getGuild().getMembers()) {
                if (m.getUser().getAsTag().equalsIgnoreCase("Elorya#5162")) {
                    event.getChannel().sendMessage(m.getUser().getAsMention()).queue();
                }
            }
        }
        */

/*
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        for (Command c : commands) {
            String prefixLabel = c.getPrefix() + c.getLabel();
            String[] test = args[0].split(c.getPrefix());
            if (prefixLabel.equalsIgnoreCase(args[0]) || test.length > 1 && c.isAlias(test[1])) {
                c.executerCommande(event);
            }
        }

    }
 */
}