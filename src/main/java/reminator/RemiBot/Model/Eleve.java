package reminator.RemiBot.Model;

import net.dv8tion.jda.api.entities.User;

import java.util.Objects;

public class Eleve {

    private final User user;

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
}
