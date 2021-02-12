package reminator.RemiBot.Model;

import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class BDDevoir {

    private Map<Eleve, ArrayList<Devoir>> devoirs;
    private static final BDDevoir instance = new BDDevoir();

    private BDDevoir() {
        this.devoirs = new HashMap<>();
    }

    public static BDDevoir getInstance() {
        return instance;
    }

    public ArrayList<Devoir> getDevoirs(User user) {
        Eleve auteur = new Eleve(user);
        if (this.devoirs.containsKey(auteur)) {
            return this.devoirs.get(auteur);
        } else {
            return new ArrayList<>();
        }
    }

    public void addDevoir(ArrayList<User> users, String course, String description, boolean all) {
        for (User user : users) {
            Eleve eleve = new Eleve(user);
            ArrayList<Devoir> devoirs;
            if (this.devoirs.containsKey(eleve)) {
                devoirs = this.devoirs.get(eleve);
            } else {
                devoirs = new ArrayList<>();
                this.devoirs.put(eleve, devoirs);
            }
            devoirs.add(new Devoir(course, description, all));
        }
    }

    public Devoir finiDevoir(User author, int id) {
        ArrayList<Devoir> devoirs = this.devoirs.get(new Eleve(author));
        if (id <= 0 || id > devoirs.size()) {
            return null;
        }
        ArrayList<Devoir> devoirsTrie = new ArrayList<>();

        for (Devoir d : devoirs) {
            if (d.isAll()) {
                devoirsTrie.add(d);
            }
        }
        for (Devoir d : devoirs) {
            if (!d.isAll()) {
                devoirsTrie.add(d);
            }
        }
        Devoir devoir = devoirsTrie.get(id-1);
        devoirs.remove(devoir);
        return devoir;
    }
}
