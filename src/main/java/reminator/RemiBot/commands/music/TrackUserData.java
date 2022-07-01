package reminator.RemiBot.commands.music;

import net.dv8tion.jda.api.entities.User;

public class TrackUserData {

    private User commandUser;
    private User requestedUser;

    public TrackUserData() {

    }

    public TrackUserData withCommandUser(User user) {
        this.commandUser = user;
        return this;
    }

    public TrackUserData withRequestedUser(User user) {
        this.requestedUser = user;
        return this;
    }

    public User getCommandUser() {
        return commandUser;
    }

    public User getRequestedUser() {
        return requestedUser;
    }
}
