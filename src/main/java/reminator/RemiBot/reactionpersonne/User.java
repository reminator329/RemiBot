package reminator.RemiBot.reactionpersonne;

public enum User {
    ELORYA("427256562596315167", null),
    FEAVY("264490592610942976", null),
    REMINATOR("368733622246834188", null),
    MOUMOUNI("690638442899439708", null),
    KACHARAL("477513801915432970", null),
    REDECO("218790840221302784", null),
    DORIAN("151602898910838794", null),
    ERAZZED("283354207317262336", null),
    HARPIERAPACE("351109763649765376", null),
    DREAMPLUME("329712193249476609", null),
    SWAPHOLY("246736085550497793", null),
    ;

    String id;
    String authorization;

    User(String id, String authorization) {
        this.id = id;
        this.authorization = authorization;
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
