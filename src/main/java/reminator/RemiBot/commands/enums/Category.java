package reminator.RemiBot.commands.enums;

public enum Category {

    DEVOIR("Devoir", "Les commandes pour les devoirs."),
    JEU("Jeu", "Les commandes de jeux."),
    MUSIQUE("Musique", "Les commandes liées à la musique sur discord."),
    JAPONAIS("Japonais", "Les commandes pour réviser le japonais."),
    AUTRE("Autres", "Les commandes sans catégorie particulière."),
    PERSO("Personnel", "Les commandes qui s'exécutent sur une personne directement."),
    BILAL("Bilal", "Les commandes liées à Bilal."),
    SCANNER("Scanneur", "Les commandes liées au scanneur de prix."),
    ADMIN("Administrateur", "Les commandes pour la gestion administrative de serveur discord.")
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
