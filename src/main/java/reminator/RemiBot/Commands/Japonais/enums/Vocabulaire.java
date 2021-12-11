package reminator.RemiBot.Commands.Japonais.enums;

import reminator.RemiBot.Commands.Japonais.VocabulaireCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Vocabulaire {
    QUAND("Quand", "いつ"),
    OU_LIEU("Où", "どこ"),
    POURQUOI("Pourquoi", "なぜ"),
    QUI("Qui", "だれ"),
    QUOI("Quoi", "なに"),
    QUEL("Quel", "どれ"),
    QUEL_OBJET("Quel (objet)", "どの"),
    PARCE_QUE("Parce que", "から"),
    ;

    String fr;
    List<CharJP> japonais = new ArrayList<>();

    Vocabulaire(String fr, String jp) {
        this.fr = fr;
        for (char c : jp.toCharArray()) {
            System.out.println(jp.toCharArray());
            CharJP charJP = Hiragana.parse(c);
            if (charJP != null) {
                japonais.add(charJP);
                continue;
            }
            charJP = Katakana.parse(c);
            if (charJP != null) {
                japonais.add(charJP);
                continue;
            }
            charJP = Kanji.parse(c);
            if (charJP != null) {
                japonais.add(charJP);
            }
        }
    }

    public boolean equals(String s) {
        boolean roomaji = false;
        Pattern pattern = Pattern.compile("([a-zA-Z])");
        Matcher matcher = pattern.matcher(String.valueOf(s.charAt(0)));
        if(matcher.find()) {
            System.out.println("OUIIIIIIIIIIIIIIIIIII");
            roomaji = true;
        }
        int i = 0;
        for (CharJP charJP : this.japonais) {
            if (roomaji) {
                String r = charJP.roomaji();
                System.out.println(r);
                int len = r.length();
                if (s.length() < i + len - 1) return false;

                StringBuilder test = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    test.append(s.charAt(i + j));
                }
                i += len;
                if (!test.toString().equalsIgnoreCase(charJP.roomaji())) return false;
            } else {
                if (!String.valueOf(s.charAt(i)).equalsIgnoreCase(charJP.japonais())) return false;
                i++;
            }
        }
        return true;
    }

    public String japonais() {
        StringBuilder res = new StringBuilder();
        for (CharJP charJP : japonais) {
            System.out.println(japonais.size());
            res.append(charJP.japonais());
        }
        System.out.println(res);
        return res.toString();
    }

    public String fr() {
        return this.fr;
    }

    public static Vocabulaire getRandom() {
        return Vocabulaire.values()[new Random().nextInt(Vocabulaire.values().length)];
    }
}
