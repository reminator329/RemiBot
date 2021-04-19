package reminator.RemiBot.Model;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Date;

public class Devoir implements Comparable<Devoir> {

    private final String course;
    private final String description;
    private final boolean all;
    private Date date = null;

    public Devoir(String course, String description, boolean all) {
        this.course = course;
        this.description = description;
        this.all = all;
    }

    public Devoir(String course, String description, boolean all, Date date) {
        this(course, description, all);
        this.date = date;
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

    public Date getDate() {
        return date;
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
        if (date != null)
            json.put("date", date.getTime());
        return json;
    }

    @Override
    public int compareTo(@NotNull Devoir o) {
        if (date == null || o.getDate() == null || date.compareTo(o.getDate()) == 0) {
            if (course.compareTo(o.getCourse()) == 0) {
                return description.compareTo(o.getDescription());
            } else {
                return course.compareTo(o.getCourse());
            }
        }
        return date.compareTo(o.getDate());
    }
}
