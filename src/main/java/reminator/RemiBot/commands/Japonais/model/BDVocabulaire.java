package reminator.RemiBot.commands.Japonais.model;

import java.util.*;

public class BDVocabulaire {

    private static final Random RAND = new Random();

    private final Set<Vocabulaire> vocabulaires;

    protected BDVocabulaire() {
        vocabulaires = new HashSet<>();
    }

    public void addVocabulaire(Vocabulaire vocabulaire) {
        vocabulaires.add(vocabulaire);
    }

    public boolean isEmpty() {
        return this.vocabulaires.isEmpty();
    }

    public Vocabulaire getRandomVocabulary() {
        int index = RAND.nextInt(this.vocabulaires.size());

        Iterator<Vocabulaire> it = this.vocabulaires.iterator();
        Vocabulaire result = null;
        for (int i = 0; it.hasNext() && i <= index; i++)
            result = it.next();

        return result;
    }
}
