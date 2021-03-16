package reminator.RemiBot.Model;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public abstract class BDDevoir {

    public static final ArrayList<Eleve> eleves = new ArrayList<>();

    public static int HEURE_DEFAULT = 18;

    public abstract ArrayList<Devoir> getDevoirs(User author);

    public abstract void addDevoir(ArrayList<User> users, String arg, String toString, boolean all);

    public abstract Devoir finiDevoir(User author, int numeroDevoir);

    public abstract void setRappel(User user, boolean b, int heure);

    public abstract int getHeure(User user);

    public abstract boolean getStatut(User user);

    public void ajoutTimer(Eleve e) {
        if (!eleves.contains(e)) {
            eleves.add(e);
        }
    }

    public Eleve getEleve(User user) {
        for (Eleve e : eleves) {
            if (e.getUser().getId().equals(user.getId())) {
                return e;
            }
        }
        return null;
    }
}
