package reminator.RemiBot.edt;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.json.JSONArray;
import org.json.JSONObject;
import reminator.RemiBot.music.HTTPRequest;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Edt {

    private String edt;

    public Edt() {
        updateEdt();
    }

    public JSONObject getNextCourse() {
        updateEdt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = new Date();

        JSONArray courss = new JSONObject(edt).getJSONArray("items");
        JSONObject prochainCours = null;
        for (int i=0; i<courss.length(); i++) {
            JSONObject cours = courss.getJSONObject(i);

            String summary = cours.getString("summary");
            if (summary.contains("ELU")) {
                if (prochainCours == null) {
                    prochainCours = cours;
                } else {
                    try {
                        Date nouveauPCours = dateFormat.parse(cours.getJSONObject("start").getString("dateTime"));
                        Date pCours = dateFormat.parse(prochainCours.getJSONObject("start").getString("dateTime"));
                        if ((new Date (date.getTime() - 500 * 3600)).compareTo(nouveauPCours) < 0 && nouveauPCours.compareTo(pCours) < 0) {
                            prochainCours = cours;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return prochainCours;
    }

    public String[] getTypeCours(JSONObject cours) {
        String[] type = new String[2];
        try {
            String csv = new HTTPRequest("https://docs.google.com/spreadsheets/u/1/d/13SY9w4EKKCH4v5Sbi6z0qF3-hpl9XE5_cv3xC4tn67M/export?format=csv&id=13SY9w4EKKCH4v5Sbi6z0qF3-hpl9XE5_cv3xC4tn67M&gid=0").GET();

            SimpleDateFormat formatJour = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatCours = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

            Date date = formatCours.parse(cours.getJSONObject("start").getString("dateTime"));

            String[] joursListe = csv.split("\n");
            String jour = null;
            for (String s : joursListe) {
                String[] jourList = s.split(",");
                if(formatJour.format(date).equals(jourList[0])) {
                    jour = s;
                }
            }

            if (jour != null) {
                String[] jourList = jour.split(",");
                for (int i=2; i<12; i+=3) {
                    String heure = jourList[i];
                    if (heure.equals(formatHeure.format(date))) {
                        type[0] = jourList[i+1];
                        if (jourList.length > i+2) {
                            type[1] = jourList[i + 2];
                        }
                    }
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return type;
    }

    private void updateEdt() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = new Date();

        try {
            this.edt = new HTTPRequest("https://clients6.google.com/calendar/v3/calendars/jjr0au21evqc6guauvan3034ug@group.calendar.google.com/events?calendarId=jjr0au21evqc6guauvan3034ug%40group.calendar.google.com&singleEvents=true&timeZone=Europe%2FParis&maxAttendees=1&maxResults=250&sanitizeHtml=true&" +
                    "timeMin=" + dateFormat.format(date).replace(":", "%3A").replace("+", "%2B") + "&" +
                    "timeMax=" + dateFormat.format(new Date(date.getTime() + 1000 * 3600 * 24 * 7)).replace(":", "%3A").replace("+", "%2B") + "&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printCourse(JSONObject cours, MessageChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("'Le 'dd/MM' à 'HH:mm");
        String[] typeCours = getTypeCours(cours);

        builder.setColor(Color.RED);
        builder.setTitle("Prochain cours");
        builder.appendDescription(cours.getString("summary"));
        try {
            builder.addField("Date", dateFormat2.format(new Date(dateFormat1.parse(cours.getJSONObject("start").getString("dateTime")).getTime())), false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        builder.addField("Type", typeCours[0], false);
        if (typeCours[1] != null && typeCours[1] != "") {
            if (typeCours[1].contains("discord")) {
                builder.addField("Discord", typeCours[1], false);
            } else if ( typeCours[1].contains("zoom")) {
                builder.addField("Zoom", typeCours[1], false);
            } else {
                builder.addField("Lien", typeCours[1], false);
            }
        }
        channel.sendMessage(builder.build()).queue();
    }
}
