package reminator.RemiBot.Commands.Japonais.model;

import reminator.RemiBot.Commands.Japonais.enums.CharJP;
import reminator.RemiBot.Commands.Japonais.enums.Kanji;
import reminator.RemiBot.Commands.Japonais.enums.kanas.*;
import reminator.RemiBot.utils.HTTPRequest;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class VocabulaireParserCSV {

    private static final VocabulaireParserCSV instance = new VocabulaireParserCSV();

    private String url;
    private String csv;

    private final Map<Categorie, Set<Vocabulaire>> vocabulaireByCategory = new HashMap<>();

    private VocabulaireParserCSV() {
        this.url = null;
        this.csv = null;
    }

    public static VocabulaireParserCSV getInstance() {
        return instance;
    }

    public VocabulaireParserCSV setURL(String url) {
        this.url = url;
        return this;
    }

    public VocabulaireParserCSV update() {
        if (this.url == null) return this;
        try {
            this.csv = new HTTPRequest(this.url).GET();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] vocabulaires = csv.split("\n");

        for (String s : vocabulaires) {
            String[] vocabulaire = s.split(",");
            if (vocabulaire.length == 0) continue;
            int index = 0;
            if (!"1".equalsIgnoreCase(vocabulaire[index])) continue;
            index++;

            // 1
            String roomaji = vocabulaire[index];
            index++;

            // 2
            String fr = vocabulaire[index];
            index++;

            // 3
            String jp = vocabulaire[index];
            List<CharJP> japonais = getJaponais(jp);
            index++;

            // 4
            Set<Categorie> categories = Arrays.stream(vocabulaire[index].split(";")).map(Categorie::new).collect(Collectors.toSet());

            for (Categorie categorie : categories) {
                vocabulaireByCategory.computeIfAbsent(categorie, c -> new HashSet<>()).add(new Vocabulaire(fr, japonais, roomaji, categories));
            }
        }
        System.out.println(csv);
        return this;
    }

    public BDVocabulaire generateBDVocabulaire(Set<Categorie> categories) {
        BDVocabulaire bd = new BDVocabulaire();
        if (categories.size() == 0) {
            for (Collection<Vocabulaire> vocabulaires : vocabulaireByCategory.values()) {
                vocabulaires.forEach(bd::addVocabulaire);
            }
        } else {
            for (Categorie categorie : categories) {
                if (vocabulaireByCategory.containsKey(categorie)) {
                    vocabulaireByCategory.get(categorie).forEach(bd::addVocabulaire);
                }
            }
        }
        return bd;
    }

    private List<CharJP> getJaponais(String jp) {

        List<CharJP> japonais = new ArrayList<>();

        for (int i = 0; i < jp.length(); i++) {
            char c1 = jp.charAt(i);
            Character c2 = (i + 1) >= jp.length() ? null : jp.charAt(i + 1);
            CharJP charJp;

            if (c2 != null) {

                charJp = CombinaisonHiragana.parse(c1, c2);
                if (charJp != null) {
                    japonais.add(charJp);
                    i++;
                    continue;
                }

                charJp = CombinaisonKatakana.parse(c1, c2);
                if (charJp != null) {
                    japonais.add(charJp);
                    i++;
                    continue;
                }
            }


            charJp = PetitHiragana.parse(c1);
            if (charJp != null) {
                japonais.add(charJp);
                continue;
            }
            charJp = PetitKatakana.parse(c1);
            if (charJp != null) {
                japonais.add(charJp);
                continue;
            }
            charJp = Hiragana.parse(c1);
            if (charJp != null) {
                japonais.add(charJp);
                continue;
            }
            charJp = Katakana.parse(c1);
            if (charJp != null) {
                japonais.add(charJp);
                continue;
            }
            charJp = Kanji.parse(c1);
            if (charJp != null) {
                japonais.add(charJp);
            }
        }
        return japonais;
    }

    public String getCsv() {
        return csv;
    }

    public String getUrl() {
        return url;
    }
}
