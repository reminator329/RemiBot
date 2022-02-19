package reminator.RemiBot.Commands.Japonais.enums.kanas;

import reminator.RemiBot.Commands.Japonais.enums.CharJP;

import java.util.Random;

public enum Hiragana implements CharJP {
    a('あ'), i('い'), u('う'), e('え'), o('お'),

    ka('か'), ki('き'), ku('く'), ke('け'), ko('こ'),
    ga('が'), gi('ぎ'), gu('ぐ'), ge('げ'), go('ご'),

    sa('さ'), shi('し'), su('す'), se('せ'), so('そ'),
    za('ざ'), zi('じ'), zu('ず'), ze('ぜ'), zo('ぞ'),

    ta('た'), chi('ち'), tsu('つ'), te('て'), to('と'),
    da('だ'), di('ぢ'), du('づ'), de('で'), do_('ど'),

    na('な'), ni('に'), nu('ぬ'), ne('ね'), no('の'),

    ha('は'), hi('ひ'), fu('ふ'), he('へ'), ho('ほ'),
    ba('ば'), bi('び'), bu('ぶ'), be('べ'), bo('ぼ'),
    pa('ぱ'), pi('ぴ'), pu('ぷ'), pe('ぺ'), po('ぽ'),

    ma('ま'), mi('み'), mu('む'), me('め'), mo('も'),

    ya('や'), yu('ゆ'), yo('よ'),

    ra('ら'), ri('り'), ru('る'), re('れ'), ro('ろ'),

    wa('わ'), wo('を'), n('ん');

    char hiragana;

    Hiragana(char hiragana) {
        this.hiragana = hiragana;
    }

    @Override
    public String japonais() {
        return this.hiragana + "";
    }

    public String roomaji() {
        // do_ car do est un mot clé java
        if (this.name().equals("do_")) return "do";
        return this.name();
    }

    public static Hiragana getRandom() {
        return Hiragana.values()[new Random().nextInt(Hiragana.values().length)];
    }

    public static Hiragana parse(char c) {
        for (Hiragana h : Hiragana.values()) {
            if (h.japonais().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }
}
