package reminator.RemiBot.Commands.Devoir;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Model.BDDevoir;
import reminator.RemiBot.Model.BDDevoirJson;
import reminator.RemiBot.Model.Eleve;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevoirRappelCommand extends Command {

    private static final BDDevoir bdDevoir = BDDevoirJson.getInstance();

    public DevoirRappelCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("devoir-rappel");
        this.addAlias("d-r");
        this.addAlias("dr");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande devoir-rappel");
        builder.appendDescription("Permet d'activer ou désactiver le rappel de devoirs tous les jours.");
        builder.addField("Signature", "`r!devoir-rappel <[statut(true|false)] [heure(HH)]>`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        User user = event.getAuthor();
        bdDevoir.ajoutTimer(new Eleve(user));

        if (args.length < 2) {

            String messageFin;

            int h = bdDevoir.getHeure(user);
            if (h < 0) {
                return;
            }
            boolean s = bdDevoir.getStatut(user);

            if (s) {
                messageFin = "Le rappel des devoirs est activé.";

                messageFin += "\n";
                if (h >= 2) {
                    messageFin += "L'heure du rappel est défini à " + h + " heures";
                } else {
                    messageFin += "L'heure du rappel est défini à " + h + " heure";
                }

                if (h == BDDevoir.HEURE_DEFAULT) {
                    messageFin += " (heure par défault)";
                }
                messageFin += ".";

            } else {
                messageFin = "Le rappel des devoirs n'est pas activé.";
            }
            bdDevoir.setRappel(user, s, h);
            channel.sendMessage(messageFin).queue();

            return;
        }

        boolean first = true;
        boolean flagStatut = false;
        boolean statut = false;
        boolean flagHeure = false;
        int heure = -1;

        boolean fin;
        boolean erreur = false;
        StringBuilder messageErreur = new StringBuilder("**Erreur**\n");

        Pattern patternHeure = Pattern.compile("([0-9]+)");
        for (String arg : args) {
            if (first) {
                first = false;
                continue;
            }
            if (arg.equalsIgnoreCase("false")) {
                flagStatut = true;
                statut = false;
            } else if (arg.equalsIgnoreCase("true")) {
                flagStatut = true;
                statut = true;
            } else if (patternHeure.matcher(arg).find()) {
                flagHeure = true;
                heure = Integer.parseInt(arg);
                if (heure < 0) {
                    messageErreur.append("Heure négative : ").append(arg).append("\n");
                    heure = -1;
                } else {
                    heure = heure % 24;
                }
            } else {
                erreur = true;
                messageErreur.append("Argument inconu : ").append(arg).append("\n");
            }
            fin = flagHeure && flagStatut;
            if (fin) break;
        }

        if (erreur) {
            channel.sendMessage(messageErreur.toString()).queue();
        }

        bdDevoir.setRappel(user, statut, heure);
        Eleve e = bdDevoir.getEleve(user);
        if (e != null) {
            e.setHeure(heure);
            e.setStatut(statut);
        }

        String messageFin;

        if (!flagStatut) {
            messageFin = "Le rappel des devoirs a été désactivé (statut par défaut = false)";
        } else {
            if (statut) {
                messageFin = "Le rappel des devoirs a été activé (statut = true)";

                messageFin += "\n";

                if (flagHeure) {
                    if (heure >= 2) {
                        messageFin += "L'heure du rappel est défini à " + heure + " heures";
                    } else {
                        messageFin += "L'heure du rappel est défini à " + heure + " heure";
                    }
                } else {
                    heure = bdDevoir.getHeure(user);

                    if (heure >= 2) {
                        messageFin += "L'heure du rappel est défini à " + heure + " heures";
                    } else {
                        messageFin += "L'heure du rappel est défini à " + heure + " heure";
                    }

                    if (heure == BDDevoir.HEURE_DEFAULT) {
                        messageFin += " (heure par défault)";
                    }
                }

            } else {
                messageFin = "Le rappel des devoirs a été désactivé (statut = false)";
            }
        }
        channel.sendMessage(messageFin).queue();
    }
}
