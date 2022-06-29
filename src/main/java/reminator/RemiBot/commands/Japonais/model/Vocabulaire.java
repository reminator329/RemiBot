package reminator.RemiBot.commands.Japonais.model;


import reminator.RemiBot.commands.Japonais.enums.CharJP;
import reminator.RemiBot.commands.Japonais.enums.kanas.Combinaison;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vocabulaire {

    private final String fr;
    private final List<CharJP> japonais;
    private final String roomaji;
    private final Set<Categorie> categories;

    public Vocabulaire(String fr, List<CharJP> japonais, String roomaji, Set<Categorie> categories) {
        this.fr = fr;
        this.japonais = japonais;
        this.roomaji = roomaji;
        this.categories = categories;
    }

    public boolean isCorrect(String s) {
        System.out.println(s);
        Pattern pattern = Pattern.compile("([a-zA-Z])");
        Matcher matcher = pattern.matcher(String.valueOf(s.charAt(0)));
        if (matcher.find()) {
            /*
             * Cas où la personne écrit en roomaji
             */
            return this.roomaji.equalsIgnoreCase(s);
        }

        int i = 0;
        for (CharJP charJP : this.japonais) {
            /* TODO cas où la personne écrit sans kanji (ou mixte)
                if (charJP instanceof Kanji) {
                    Kanji.parse(s.charAt(i))
                } else {
                }
                */

            if (charJP instanceof Combinaison) {
                char c1 = s.charAt(i);
                Character c2 = (i + 1) >= s.length() ? null : s.charAt(i + 1);

                if (c2 == null) return false;
                if (!("" + c1 + c2).equalsIgnoreCase(charJP.japonais())) return false;
                i++;

            } else {
                if (!String.valueOf(s.charAt(i)).equalsIgnoreCase(charJP.japonais())) return false;
            }


            i++;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vocabulaire that = (Vocabulaire) o;
        return Objects.equals(fr, that.fr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fr, japonais, roomaji, categories);
    }

    public String getFr() {
        return fr;
    }

    public String getJaponais() {
        StringBuilder res = new StringBuilder();
        for (CharJP charJP : japonais) {
            res.append(charJP.japonais());
        }
        return res.toString();
    }

    public String getRoomaji() {
        return roomaji;
    }

    public Set<Categorie> getCategories() {
        return new HashSet<>(categories);
    }

    @Override
    public String toString() {
        return this.fr;
    }
}
