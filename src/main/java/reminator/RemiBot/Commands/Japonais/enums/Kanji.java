package reminator.RemiBot.Commands.Japonais.enums;

import reminator.RemiBot.Commands.Japonais.enums.kanas.Hiragana;
import reminator.RemiBot.Commands.Japonais.enums.kanas.Katakana;

import java.util.ArrayList;
import java.util.List;

public enum Kanji implements CharJP {
    POISSON('魚', "さかな"),
    ALIMENTS('食', "た"),
    BOISSONS('飲', "の"),
    VIANDE('肉', "にく"),
    TAMAGO('卵', "たまご"),
    MIZU('水', "みず"),
    OO('大', "おお"),
    CHIISAI('小', "ちい"),
    ATARA('新', "あたら"),
    FURU('古', "ふる"),

    ICHI('一', "いち"),
    NI('二', "に"),
    SAN('三', "さん"),
    YON('四', "よん"),
    GO('五', "ご"),
    ROKU('六', "ろく"),
    NANA('七', "なな"),
    HACHI('八', "はち"),
    KYUU('九', "きゅう"),
    JUU('十', "じゅう"),

    JI('時', "じ"),
    FUN('分', "ふん")
    ;

    char kanji;
    List<CharJP> japonais = new ArrayList<>();

    Kanji(char kanji, String jp) {
        this.kanji = kanji;

        for (char c : jp.toCharArray()) {
            CharJP charJP = Hiragana.parse(c);
            if (charJP != null) {
                japonais.add(charJP);
                continue;
            }
            charJP = Katakana.parse(c);
            if (charJP != null) {
                japonais.add(charJP);
            }
        }
    }

    public static Kanji parse(char c) {
        for (Kanji h : Kanji.values()) {
            if (h.toString().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(kanji);
    }

    @Override
    public String roomaji() {
        StringBuilder res = new StringBuilder();
        for (CharJP charJP : japonais) {
            res.append(charJP.roomaji());
        }
        return res.toString();
    }

    @Override
    public String japonais() {
        return String.valueOf(this.kanji);
    }
}
