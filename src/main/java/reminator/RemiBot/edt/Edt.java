package reminator.RemiBot.edt;

import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.json.JSONArray;
import org.json.JSONObject;
import reminator.RemiBot.music.HTTPRequest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Edt {

    private String edt;

    public Edt() {
        updateEdt();
    }

    public JSONObject getNextCourse() {
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
                        if (nouveauPCours.compareTo(pCours) < 0) {
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
}
