package reminator.RemiBot.Services.reactionpersonne;

public enum User {
    ELORYA("427256562596315167"),
    FEAVY("264490592610942976"),
    REMINATOR("368733622246834188"),
    MOUMOUNI("690638442899439708"),
    KACHARAL("477513801915432970"),
    REDECO("218790840221302784"),
    DORIAN("151602898910838794"),
    ERAZZED("283354207317262336"),
    HARPIERAPACE("351109763649765376"),
    DREAMPLUME("329712193249476609"),
    SWAPHOLY("246736085550497793"),
    YAEL("690593377250443374"),
    ALPHATASH("280674572108300288")
    ;

    String id;
    String authorization;

    User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
