package reminator.RemiBot.commands.Japonais.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Categorie implements Comparable<Categorie> {

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

    @Override
    public int compareTo(@NotNull Categorie o) {
        return this.nom.compareTo(o.nom);
    }
}
