package reminator.RemiBot.Commands.Japonais.enums;

import java.util.Random;

public enum CombinaisonKatakana implements Combinaison {
    KYA("キャ"), KYU("キュ"), KYO("キョ"),
    GYA("ギャ"), GYU("ギュ"), GYO("ギョ"),

    SHA("シャ"), SHU("シュ"), SHO("ショ"),
    JA("ジャ"), JU("ジュ"), JO("ジョ"),

    NYA("ニャ"), NYU("ニュ"), NYO("ニョ"),

    HYA("ヒャ"), HYU("ヒュ"), HYO("ヒョ"),
    BYA("ビャ"), BYU("ビュ"), BYO("ビョ"),
    PYA("ピャ"), PYU("ピュ"), PYO("ピョ"),

    RYA("リャ"), RYU("リュ"), RYO("リョ"),
    ;



    Katakana katakana;
    PetitKatakana p_katakana;

    CombinaisonKatakana(String combinaison) {
        this.katakana = Katakana.parse(combinaison.charAt(0));
        this.p_katakana = PetitKatakana.parse(combinaison.charAt(1));
    }

    @Override
    public String japonais() {
        return "" + this.katakana.japonais() + this.p_katakana.japonais();
    }

    public String roomaji() {
        return this.name();
    }

    public static CombinaisonKatakana getRandom() {
        return CombinaisonKatakana.values()[new Random().nextInt(CombinaisonKatakana.values().length)];
    }

    public static CombinaisonKatakana parse(char c1, Character c2) {
        if (c2 == null) return null;

        for (CombinaisonKatakana h : CombinaisonKatakana.values()) {
            if (h.japonais().equalsIgnoreCase("" + c1 + c2)) {
                return h;
            }
        }
        return null;
    }
}
