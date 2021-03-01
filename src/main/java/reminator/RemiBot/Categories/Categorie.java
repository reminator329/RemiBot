package reminator.RemiBot.Categories;

import reminator.RemiBot.Commands.Command;

import java.util.ArrayList;

public abstract class Categorie {

    private String nom;
    private String description;
    private final ArrayList<Command> commands = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(commands);
    }

    public void addCommand(Command c) {
        if (!commands.contains(c)) {
            commands.add(c);
        }
    }
}
