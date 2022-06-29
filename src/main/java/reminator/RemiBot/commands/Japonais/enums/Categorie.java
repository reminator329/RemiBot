package reminator.RemiBot.commands.Japonais.enums;

public enum Categorie {
    S7("s7"),
    S8("s8"),
    POSITION("position"),
    MAISON("maison"),
    ;

    String name;

    Categorie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Categorie parse(String s) {
        for (Categorie c : Categorie.values()) {
            if (c.getName().equalsIgnoreCase(s)) {
                return c;
            }
        }
        return null;
    }
}
