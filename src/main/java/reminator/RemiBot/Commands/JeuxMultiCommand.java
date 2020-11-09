package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import reminator.RemiBot.bot.RemiBot;
import reminator.RemiBot.music.HTTPRequest;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JeuxMultiCommand extends Command{

    String csv;

    public JeuxMultiCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("jeux-multi");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande jeux-multi");
        builder.appendDescription("Affiche les jeux communs des membres de la Secte");

        builder.addField("Signature", "`r!jeux-multi <joueurs>`", false);

        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        updateCsv();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);

        String[] lignes = csv.split("\n");
        ArrayList<String> jeux = new ArrayList<>();

        if (args.length == 1) {
            int taille = lignes[0].split(",").length;
            boolean flag = true;
            for (int i = 2; i < lignes.length; i++) {
                String[] ligne = lignes[i].split(",");
                System.out.println(ligne[0]);
                if (ligne.length == taille) {
                    for (int j = 2; j < taille; j = j + 2) {
                        if (ligne[j] != null) {
                            if (!ligne[j].toUpperCase().contains("oui".toUpperCase())) {
                                System.out.println("oui");
                                flag = false;
                                break;
                            }
                        } else {
                            System.out.println("oui");
                            flag = false;
                            break;
                        }
                        System.out.println(ligne[j].toUpperCase());
                    }
                } else {
                    flag = false;
                }
                if (flag) {
                    System.out.println("non");
                    jeux.add(ligne[0]);
                }
                flag = true;
            }
        } else {
            int nbJoueurs = args.length - 1;

            boolean flag = true;
            for (int i = 2; i < lignes.length; i++) {
                String[] ligne = lignes[i].split(",");
                System.out.println(ligne[0]);
                    for (int j = 2; j < ligne.length; j = j + 2) {
                        System.out.println(lignes[0].split(",")[j]);
                        if (estJoueur(lignes[0].split(",")[j], args)) {
                            System.out.println(lignes[0].split(",")[j]);
                            if (j < ligne.length) {
                                if (ligne[j] != null) {
                                    if (!ligne[j].toUpperCase().contains("oui".toUpperCase())) {
                                        flag = false;
                                        break;
                                    }
                                } else {
                                    flag = false;
                                    break;
                                }
                            } else {
                                flag = false;
                                break;
                            }
                            nbJoueurs--;
                        }
                        System.out.println(nbJoueurs);
                        if (nbJoueurs == 0) {
                            break;
                        }
                    }
                if (flag && nbJoueurs == 0) {
                    System.out.println("non");
                    jeux.add(ligne[0]);
                }
                flag = true;
                nbJoueurs = args.length - 1;
            }
        }

        String jeuxS = "";
        for (String jeu : jeux) {
            jeuxS += jeu + "\n";
        }


        builder.setTitle("Jeux en commun :");
        builder.addField(" ", jeuxS, false);
        channel.sendMessage(builder.build()).queue();
    }

    private boolean estJoueur(String s, String[] args) {
        ArrayList<String> joueurs = new ArrayList<>();
        for (int i=1; i<args.length; i++) {
            joueurs.add(args[i]);
        }
        for (String joueur : joueurs) {
            if (s.toUpperCase().contains(joueur.toUpperCase()) || joueur.toUpperCase().contains(s.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


    private void updateCsv() {
        try {
            csv = new HTTPRequest("https://docs.google.com/spreadsheets/d/1UZDKdy1rgVLYrCqS6ux7b0hMEUT_0hAl8zaBv9Cy_QU/export?format=csv&gid=0").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
