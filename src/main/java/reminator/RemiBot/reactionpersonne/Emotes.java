package reminator.RemiBot.reactionpersonne;

public enum Emotes {
    OUI("773124334339620864"),
    NON("773124709096488990");

    private final String id;

    Emotes(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
