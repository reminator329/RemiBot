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
import java.util.ArrayList;
import java.util.Date;

public class Edt {

    private String edt01;
    private String edt02;
    private String edt1;
    private String edt2;

    ArrayList<Cours> prochainCours = new ArrayList<>();
    ArrayList<Cours> courses;
    String csv;

    public Edt() {
        courses = new ArrayList<>();
    }

    public ArrayList<Cours> getNextCourse() {
        updateEdt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = new Date();

        prochainCours.clear();
        for (Cours c : courses) {
            String summary = c.getSummary();
            if (summary.contains("ELU")) {
                if (prochainCours.size() == 0) {
                    prochainCours.add(c);
                } else {
                    try {
                        Date nouveauPCours = dateFormat.parse(c.getStart());
                        Date pCours = dateFormat.parse(prochainCours.get(0).getStart());
                        if (nouveauPCours.compareTo(pCours) == 0) {
                            prochainCours.add(c);
                        } else if ((new Date (date.getTime() - 500 * 3600)).compareTo(nouveauPCours) < 0 && nouveauPCours.compareTo(pCours) < 0) {
                            prochainCours.clear();
                            prochainCours.add(c);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return prochainCours;
    }

    public String[] getTypeCours(Cours cours) {
        String[] type = new String[2];
        try {

            SimpleDateFormat formatJour = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatCours = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

            Date date = formatCours.parse(cours.getStart());

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
                for (int i=2; i<jourList.length - 2; i+=3) {
                    String heure = jourList[i];
                    if (heure.equals(formatHeure.format(date))) {
                        type[0] = jourList[i+1];
                        type[1] = jourList[i + 2];
                    }
                }
            }

        } catch (ParseException | NullPointerException ignored) {
        }
        return type;
    }

    private void updateEdt() {
        courses.clear();
        updateCsv();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = new Date();

        try {
            this.edt01 = new HTTPRequest("https://clients6.google.com/calendar/v3/calendars/jjr0au21evqc6guauvan3034ug@group.calendar.google.com/events?calendarId=jjr0au21evqc6guauvan3034ug%40group.calendar.google.com&singleEvents=true&timeZone=Europe%2FParis&maxAttendees=1&maxResults=250&sanitizeHtml=true&" +
                    "timeMin=" + dateFormat.format(date).replace(":", "%3A").replace("+", "%2B") + "&" +
                    "timeMax=" + dateFormat.format(new Date(date.getTime() + 1000 * 3600 * 24 * 7)).replace(":", "%3A").replace("+", "%2B") + "&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.edt02 = new HTTPRequest("https://clients6.google.com/calendar/v3/calendars/8nam511995lbsisujjcq80h964@group.calendar.google.com/events?calendarId=8nam511995lbsisujjcq80h964@group.calendar.google.com&singleEvents=true&timeZone=Europe/Paris&maxAttendees=1&maxResults=250&sanitizeHtml=true&" +
                    "timeMin=" + dateFormat.format(date).replace(":", "%3A").replace("+", "%2B") + "&" +
                    "timeMax=" + dateFormat.format(new Date(date.getTime() + 1000 * 3600 * 24 * 7)).replace(":", "%3A").replace("+", "%2B") + "&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.edt1 = new HTTPRequest("https://clients6.google.com/calendar/v3/calendars/4jpbp5hcdimlmov6kscioe4am8@group.calendar.google.com/events?calendarId=4jpbp5hcdimlmov6kscioe4am8@group.calendar.google.com&singleEvents=true&timeZone=Europe/Paris&maxAttendees=1&maxResults=250&sanitizeHtml=true&" +
                    "timeMin=" + dateFormat.format(date).replace(":", "%3A").replace("+", "%2B") + "&" +
                    "timeMax=" + dateFormat.format(new Date(date.getTime() + 1000 * 3600 * 24 * 7)).replace(":", "%3A").replace("+", "%2B") + "&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.edt2 = new HTTPRequest("https://clients6.google.com/calendar/v3/calendars/e44ep4hdrj5b2defqf9mmcpd2k@group.calendar.google.com/events?calendarId=e44ep4hdrj5b2defqf9mmcpd2k@group.calendar.google.com&singleEvents=true&timeZone=Europe/Paris&maxAttendees=1&maxResults=250&sanitizeHtml=true&" +
                    "timeMin=" + dateFormat.format(date).replace(":", "%3A").replace("+", "%2B") + "&" +
                    "timeMax=" + dateFormat.format(new Date(date.getTime() + 1000 * 3600 * 24 * 7)).replace(":", "%3A").replace("+", "%2B") + "&key=AIzaSyBNlYH01_9Hc5S1J9vuFmu2nUqBZJNAXxs").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jEdt01 = new JSONObject(edt01);
        JSONObject jEdt02 = new JSONObject(edt02);
        JSONObject jEdt1 = new JSONObject(edt1);
        JSONObject jEdt2 = new JSONObject(edt2);

        JSONArray jCourss01 = jEdt01.getJSONArray("items");
        JSONArray jCourss02 = jEdt02.getJSONArray("items");
        JSONArray jCourss1 = jEdt1.getJSONArray("items");
        JSONArray jCourss2 = jEdt2.getJSONArray("items");

        ajoutCourss(jCourss01);
        ajoutCourss(jCourss02);
        ajoutCourss(jCourss1);
        ajoutCourss(jCourss2);
    }

    private void updateCsv() {
        try {
            csv = new HTTPRequest("https://docs.google.com/spreadsheets/u/1/d/13SY9w4EKKCH4v5Sbi6z0qF3-hpl9XE5_cv3xC4tn67M/export?format=csv&id=13SY9w4EKKCH4v5Sbi6z0qF3-hpl9XE5_cv3xC4tn67M&gid=0").GET();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ajoutCourss(JSONArray courss) {

        for (int i=0; i<courss.length(); i++) {
            Cours c = new Cours(courss.getJSONObject(i));
            String[] type = getTypeCours(c);
            c.setType(type[0]);
            c.setLien(type[1]);
            courses.add(c);
        }
    }

    public void printCourse(Cours cours, MessageChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("'Le 'dd/MM' à 'HH:mm");

        builder.setColor(Color.RED);
        builder.setTitle("Prochain cours");
        builder.appendDescription(cours.getSummary());
        try {
            builder.addField("Date", dateFormat2.format(new Date(dateFormat1.parse(cours.getStart()).getTime())), false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        builder.addField("Type", cours.getType(), false);
        String lien = cours.getLien();
        if (lien != null && !lien.equals("")) {
            if (lien.contains("discord")) {
                builder.addField("Discord", lien, false);
            } else if (lien.contains("zoom")) {
                builder.addField("Zoom", lien, false);
            } else {
                builder.addField("Lien", lien, false);
            }
        }
        channel.sendMessage(builder.build()).queue();
    }
}
