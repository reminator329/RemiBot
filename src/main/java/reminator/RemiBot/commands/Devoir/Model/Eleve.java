package reminator.RemiBot.commands.Devoir.Model;

import net.dv8tion.jda.api.entities.User;

import java.util.Objects;

public class Eleve {

    private final User user;
    private int heure = BDDevoir.HEURE_DEFAULT;
    private boolean statut = false;

    public Eleve(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eleve eleve = (Eleve) o;
        return user.equals(eleve.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }
}
