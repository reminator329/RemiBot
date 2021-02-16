package reminator.RemiBot.Model;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public abstract class BDDevoir {

    public abstract ArrayList<Devoir> getDevoirs(User author);

    public abstract void addDevoir(ArrayList<User> users, String arg, String toString, boolean all);

    public abstract Devoir finiDevoir(User author, int numeroDevoir);
}
