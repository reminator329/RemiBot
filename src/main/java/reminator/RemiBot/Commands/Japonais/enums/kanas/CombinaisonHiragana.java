package reminator.RemiBot.Commands.Japonais.enums.kanas;

import java.util.Random;

public enum CombinaisonHiragana implements Combinaison {
    kya("きゃ"), kyu("きゅ"), kyo("きょ"),
    gya("ぎゃ"), gyu("ぎゅ"), gyo("ぎょ"),

    sha("しゃ"), shu("しゅ"), sho("しょ"),
    ja("じゃ"), ju("じゅ"), jo("じょ"),

    nya("にゃ"), nyu("にゅ"), nyo("にょ"),

    hya("ひゃ"), hyu("ひゅ"), hyo("ひょ"),
    bya("びゃ"), byu("びゅ"), byo("びょ"),
    pya("ぴゃ"), pyu("ぴゅ"), pyo("ぴょ"),

    rya("りゃ"), ryu("りゅ"), ryo("りょ"),
    ;



    Hiragana hiragana;
    PetitHiragana p_hiragana;

    CombinaisonHiragana(String combinaison) {
        this.hiragana = Hiragana.parse(combinaison.charAt(0));
        this.p_hiragana = PetitHiragana.parse(combinaison.charAt(1));
    }

    @Override
    public String japonais() {
        return "" + this.hiragana.japonais() + this.p_hiragana.japonais();
    }

    public String roomaji() {
        return this.name();
    }

    public static CombinaisonHiragana getRandom() {
        return CombinaisonHiragana.values()[new Random().nextInt(CombinaisonHiragana.values().length)];
    }

    public static CombinaisonHiragana parse(char c1, Character c2) {
        if (c2 == null) return null;

        for (CombinaisonHiragana h : CombinaisonHiragana.values()) {

            if (h.japonais().equalsIgnoreCase("" + c1 + c2)) {
                return h;
            }
        }
        return null;
    }
}
