package reminator.RemiBot.edt;

import org.json.JSONException;
import org.json.JSONObject;

public class Cours {

    private final String summary;
    private String start;
    private String end;
    private String type;
    private String lien;
    private int groupe;

    public Cours(JSONObject jCours) {
        this.summary = jCours.getString("summary");
        try {
            this.start = jCours.getJSONObject("start").getString("dateTime");
            this.end = jCours.getJSONObject("end").getString("dateTime");
        } catch (JSONException ignored) {
            this.start = null;
            this.end = null;
        }
    }

    public void setType (String type) {
        this.type = type;
    }

    public void setLien (String lien) {
        this.lien = lien;
    }

    public String getSummary() {
        return summary;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

    public String getLien() {
        return lien;
    }

    public int getGroupe() {
        return groupe;
    }
}
