package reminator.RemiBot.Model;

import org.json.JSONObject;

public class Devoir {

    private final String course;
    private final String description;
    private final boolean all;

    public Devoir(String course, String description, boolean all) {
        this.course = course;
        this.description = description;
        this.all = all;
    }

    public String getCourse() {
        return course;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAll() {
        return all;
    }

    @Override
    public String toString() {
        return "`" + course + " : " + description + "`";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("course", course);
        json.put("description", description);
        json.put("all", all);
        return json;
    }
}
