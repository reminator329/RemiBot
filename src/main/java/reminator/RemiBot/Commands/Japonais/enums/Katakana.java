package reminator.RemiBot.Commands.Japonais.enums;

import java.util.Random;

public enum Katakana implements CharJP {
    A('ア'), I('イ'), U('ウ'), E('エ'), O('オ'),

    KA('カ'), KI('キ'), KU('ク'), KE('ケ'), KO('コ'),

    SA('サ'), SHI('シ'), SU('ス'), SE('セ'), SO('ソ'),
    TA('タ'), CHI('チ'), TSU('ツ'), TE('テ'), TO('ト'),
    NA('ナ'), NI('ニ'), NU('ヌ'), NE('ネ'), NO('ノ'),
    HA('ハ'), HI('ヒ'), FU('フ'), HE('ヘ'), HO('ホ'),
    MA('マ'), MI('ミ'), MU('ム'), ME('メ'), MO('モ'),
    YA('ヤ'), YU('ユ'), YO('ヨ'),
    RA('ラ'), RI('リ'), RU('ル'), RE('レ'), RO('ロ'),
    WA('ワ'), WO('ヲ'), N('ン');

    char katakana;

    Katakana(char katakana) {
        this.katakana = katakana;
    }

    @Override
    public String japonais() {
        return this.katakana + "";
    }

    public String roomaji() {
        return this.name();
    }

    public static Katakana getRandom() {
        return Katakana.values()[new Random().nextInt(Katakana.values().length)];
    }

    public static Katakana parse(char c) {
        for (Katakana h : Katakana.values()) {
            if (h.japonais().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }
}