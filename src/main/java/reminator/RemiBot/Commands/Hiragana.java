package reminator.RemiBot.Commands;

import java.util.Random;

public enum Hiragana {
    A("あ"), I("い"), U("う"), E("え"), O("お"),
    KA("か"), KI("き"), KU("く"), KE("け"), KO("こ"),
    SA("さ"), SHI("し"), SU("す"), SE("せ"), SO("そ"),
    TA("た"), CHI("ち"), TSU("つ"), TE("て"), TO("と"),
    NA("な"), NI("に"), NU("ぬ"), NE("ね"), NO("の"),
    HA("は"), HI("ひ"), FU("ふ"), HE("へ"), HO("ほ"),
    MA("ま"), MI("み"), MU("む"), ME("め"), MO("も"),
    YA("や"), YU("ゆ"), YO("よ"),
    RA("ら"), RI("り"), RU("る"), RE("れ"), RO("ろ"),
    WA("わ"), WO("を");

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
