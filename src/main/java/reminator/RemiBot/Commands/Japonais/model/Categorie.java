package reminator.RemiBot.Commands.Japonais.model;

import java.util.Objects;

public class Categorie {

    private final String nom;

    public Categorie(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return Objects.equals(nom, categorie.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
