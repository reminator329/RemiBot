package reminator.RemiBot.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import reminator.RemiBot.commands.Devoir.Model.BDDevoir;
import reminator.RemiBot.commands.Devoir.Model.BDDevoirJson;
import reminator.RemiBot.commands.Devoir.Model.Devoir;
import reminator.RemiBot.commands.Devoir.Model.Eleve;
import reminator.RemiBot.commands.Japonais.model.VocabulaireParserCSV;
import reminator.RemiBot.Services.motdujour.MotDuJourService;
import reminator.RemiBot.Services.openai.AprilFoolService;
import reminator.RemiBot.Services.reactionpersonne.ReactionPersonneService;
import reminator.RemiBot.Services.repondeur.RepondeurService;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RemiBot {

    public static String prefix = "r!";
    public static final Color color = Color.RED;
    public static String token;
    public static JDA api;

    public static void main(String[] arguments) throws Exception {
        System.setProperty("user.timezone", "Europe/Paris");

        int index = 0;
        token = arguments[index];
        index++;

        api = JDABuilder.create(token, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_VOICE_STATES).enableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE).build();
        api.awaitReady();

        api.addEventListener(new Controller(api));
        api.addEventListener(new ReactionPersonneService(api));
        api.addEventListener(new RepondeurService(api));
        api.addEventListener(new AprilFoolService());
        api.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching("r!help"));

        try {
            VocabulaireParserCSV.getInstance().setURL(arguments[index]);
            index++;
            reminator.RemiBot.Services.reactionpersonne.User.REMINATOR.setAuthorization(arguments[index]);
            index++;
            reminator.RemiBot.Services.reactionpersonne.User.MOUMOUNI.setAuthorization(arguments[index]);
            index++;
            reminator.RemiBot.Services.reactionpersonne.User.DREAMPLUME.setAuthorization(arguments[index]);
            index++;
            reminator.RemiBot.Services.reactionpersonne.User.DORIAN.setAuthorization(arguments[index]);
        }catch (Exception e) {
            e.printStackTrace();
        }

        CommandListUpdateAction commands = api.updateCommands()
                .addCommands(
                        new CommandData("pingremi", "Retourne pong")
                                .addOption(OptionType.BOOLEAN, "ephemeral", "Mettre à true pour que personne de voit la réponse.", false)
                );
        commands.queue();

        MotDuJourService service = new MotDuJourService(api);
        service.start();

//        PriceScannerService.init(api.getTextChannelById("877259941021376512"));

        /* TODO ajouter un timer pour chaque élève grace à l'api (getUserById
        BDDevoir bdDevoir = BDDevoirJson.getInstance();
        bdDevoir.ajoutTimer(new Eleve(event.getAuthor()));
         */

        Timer timer = new Timer();
        Date date = new Date();
        long delay = 1000*3600 - (long) date.getMinutes() *60*1000 - date.getSeconds()* 1000L;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Date date = new Date();

                for (Eleve e : BDDevoir.eleves) {
                    System.out.println("test1");
                    if (!e.isStatut()) {
                        continue;
                    }
                    System.out.println("test2");
                    SimpleDateFormat heureFormat = new SimpleDateFormat("HH");

                    if (Integer.parseInt(heureFormat.format(date)) != (e.getHeure())) {
                        continue;
                    }
                    System.out.println("test3");


                    User user = e.getUser();
                    BDDevoir bdDevoir = BDDevoirJson.getInstance();
                    ArrayList<Devoir> devoirs = bdDevoir.getDevoirs(user);


                    if (devoirs.isEmpty()) {
                        user.openPrivateChannel()
                            .flatMap(channel -> channel.sendMessage("Vous n'avez pas de devoirs à faire."))
                            .queue();
                        return;
                    }

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.GREEN);
                    embedBuilder.setTitle(user.getName());
                    embedBuilder.appendDescription("Voici la liste de vos devoirs");

                    ArrayList<Devoir> devoirsAll = new ArrayList<>();
                    ArrayList<Devoir> devoirsPerso = new ArrayList<>();

                    for (Devoir d : devoirs) {
                        if (d.isAll()) {
                            devoirsAll.add(d);
                        } else {
                            devoirsPerso.add(d);
                        }
                    }

                    int numeroDevoir = 1;
                    if (!devoirsAll.isEmpty()) {
                        StringBuilder message = new StringBuilder();

                        for (Devoir d : devoirsAll) {
                            message.append("[").append(numeroDevoir).append("] **").append(d.getCourse()).append("** : ").append(d.getDescription()).append("\n");
                            numeroDevoir++;
                        }

                        embedBuilder.addField("Devoirs généraux", message.toString(), false);
                    }
                    if (!devoirsPerso.isEmpty()) {
                        StringBuilder message = new StringBuilder();

                        for (Devoir d : devoirsPerso) {
                            message.append("[").append(numeroDevoir).append("] **").append(d.getCourse()).append("** : ").append(d.getDescription()).append("\n");
                            numeroDevoir++;
                        }
                        embedBuilder.addField("Devoirs perso", message.toString(), false);
                    }

                    embedBuilder.setFooter(user.getName() + " Il vous reste " + (numeroDevoir-1) + " devoir(s) à faire.", user.getAvatarUrl());
                    e.getUser().openPrivateChannel()
                            .flatMap(channel -> channel.sendMessageEmbeds(embedBuilder.build()))
                            .queue();
                }
            }
        }, delay, 1000*3600);
    }
}