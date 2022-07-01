package reminator.RemiBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class PlusMoinsCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.JEU;
    }

    @Override
    public String getLabel() {
        return "plus-ou-moins";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"p-o-m", "pm", "p-m", "pom", "+-", "+ou-"};
    }

    @Override
    public String getDescription() {
        return "Trouve le nombre entre 0 et 1 000 000 000";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        long channelId = channel.getIdLong();
        long authorId = author.getIdLong();

        final int[] tryAmount = {1};
        int nombre = (int) (Math.random() * 1000000000);
        String commande = this.getPrefix() + this.getLabel();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Trouve le nombre entre 0 et 1 000 000 000\nÉcrit 'stop' si tu abandonnes.");
        embedBuilder.setFooter(author.getName(), author.getAvatarUrl());
        new EnvoiMessage().sendMessage(event, embedBuilder.build());

        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getChannel().getIdLong() != channelId) return;
                if (event.getAuthor().getIdLong() != authorId) return;

                String msg = event.getMessage().getContentRaw();

                if (msg.equalsIgnoreCase(commande)) {
                    event.getJDA().removeEventListener(this);
                    return;
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setFooter(author.getName(), author.getAvatarUrl());
                if (msg.equalsIgnoreCase("stop")) {
                    builder.setTitle("" + nombre);
                    builder.appendDescription("C'était le nombre à trouver !");
                    new EnvoiMessage().sendMessage(event, builder.build());
                    event.getJDA().removeEventListener(this);
                    return;
                }

                try {
                    int choix = Integer.parseInt(msg);

                    if (choix == nombre) {
                        builder.setTitle("Gagné !");
                        builder.appendDescription("C'était bien " + nombre);
                        builder.addField("Nombre de coups", "" + tryAmount[0], false);
                        new EnvoiMessage().sendMessage(event, builder.build());
                        event.getJDA().removeEventListener(this);
                        return;
                    }

                    builder.setTitle("" + choix);
                    if (choix < nombre) {
                        builder.appendDescription("C'est plus !");
                    } else {
                        builder.appendDescription("C'est moins !");
                    }
                    new EnvoiMessage().sendMessage(event, builder.build());


                    tryAmount[0]++;

                } catch (NumberFormatException ignored){
                }
            }
        });
    }
}
