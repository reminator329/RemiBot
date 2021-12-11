package reminator.RemiBot.Commands.Japonais.enums;

import java.util.Random;

public enum Hiragana {
    a("あ"), i("い"), u("う"), e("え"), o("お"),
    ka("か"), ki("き"), ku("く"), ke("け"), ko("こ"),
    sa("さ"), shi("し"), su("す"), se("せ"), so("そ"),
    ta("た"), chi("ち"), tsu("つ"), te("て"), to("と"),
    na("な"), ni("に"), nu("ぬ"), ne("ね"), no("の"),
    ha("は"), hi("ひ"), fu("ふ"), he("へ"), ho("ほ"),
    ma("ま"), mi("み"), mu("む"), me("め"), mo("も"),
    ya("や"), yu("ゆ"), yo("よ"),
    ra("ら"), ri("り"), ru("る"), re("れ"), ro("ろ"),
    wa("わ"), wo("を");

    String hiragana;

    Hiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    @Override
    public String toString() {
        return this.hiragana;
    }

    public String getName() {
        return this.name();
    }

    public static Hiragana getRandom() {
        return Hiragana.values()[new Random().nextInt(Hiragana.values().length)];
    }
}
