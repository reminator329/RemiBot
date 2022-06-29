package reminator.RemiBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.music.HTTPRequest;
import reminator.RemiBot.utils.EnvoiMessage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JeuxMultiCommand implements Command {

    String csv;

    @Override
    public Category getCategory() {
        return Category.JEU;
    }

    @Override
    public String getLabel() {
        return "jeux-multi";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"jeux", "jx", "j", "j-m", "jm"};
    }

    @Override
    public String getDescription() {
        return "Affiche les jeux communs des membres de la Secte";
    }

    @Override
    public String getSignature() {
        return Command.super.getSignature() + "[( <joueur>)+]";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        List<String> args = event.getArgs();
        Member member = event.getMember();

        updateCsv();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);

        String[] lignes = csv.split("\n");
        ArrayList<String> jeux = new ArrayList<>();

        if (args.size() == 1) {
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
            int nbJoueurs = args.size() - 1;

            boolean flag = true;
            for (int i = 2; i < lignes.length; i++) {
                String[] ligne = lignes[i].split(",");
                if (ligne.length > 1 && ligne[1] != null && !ligne[1].equals("")) {
                    if (Integer.parseInt(ligne[1]) < nbJoueurs) {
                        continue;
                    }
                }
                for (int j = 2; j < ligne.length; j = j + 2) {
                    if (estJoueur(lignes[0].split(",")[j], args)) {
                        if (ligne[j] != null) {
                            if (!ligne[j].toUpperCase().contains("oui".toUpperCase())) {
                                flag = false;
                                break;
                            }
                        } else {
                            flag = false;
                            break;
                        }
                        nbJoueurs--;
                    }
                    if (nbJoueurs == 0) {
                        break;
                    }
                }
                if (flag && nbJoueurs == 0) {
                    System.out.println("non");
                    jeux.add(ligne[0]);
                }
                flag = true;
                nbJoueurs = args.size() - 1;
            }
        }

        StringBuilder jeuxS = new StringBuilder();
        for (String jeu : jeux) {
            jeuxS.append(jeu).append("\n");
        }


        builder.setTitle("Jeux en commun :");
        builder.addField(" ", jeuxS.toString(), false);
        assert member != null;
        builder.setFooter(member.getUser().getName(), member.getUser().getAvatarUrl());
        EnvoiMessage.sendMessage(event, builder.build());
    }

    private boolean estJoueur(String s, List<String> args) {
        ArrayList<String> joueurs = new ArrayList<>(args.subList(1, args.size()));
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
