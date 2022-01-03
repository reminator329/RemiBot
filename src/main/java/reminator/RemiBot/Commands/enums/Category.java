package reminator.RemiBot.Commands.enums;

public enum Category {

    DEVOIR("Devoir", "Les commandes pour les devoirs."),
    JEU("Jeu", "Les commandes de jeux."),
    BILAL("Bilal", "Les commandes liées à Bilal."),
    AUTRE("Autres", "Les commandes sans catégorie particulière."),
    JAPONAIS("Japonais", "Les commandes pour réviser le japonais."),
    PERSO("Personnel", "Les commandes qui s'exécutent sur une personne directement."),
    N("NSFW", "Les commandes NSFW."),
    ;

    String name, description;

    Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
