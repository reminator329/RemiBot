package reminator.RemiBot.Commands.Japonais.enums;

import java.util.ArrayList;
import java.util.List;

public enum Kanji implements CharJP {
    POISSON('魚', "さかな"),
    ALIMENTS('食', "た"),
    BOISSONS('飲', "の"),
    VIANDE('肉', "にく"),
    TAMAGO('卵', "たまご"),
    MIZU('水', "みず"),
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
