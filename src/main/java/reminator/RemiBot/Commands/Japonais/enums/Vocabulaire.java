package reminator.RemiBot.Commands.Japonais.enums;

import reminator.RemiBot.Commands.Japonais.VocabulaireCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Vocabulaire {

    // EXAM 1
    /*
    QUAND("Quand", "いつ"),
    OU_LIEU("Où", "どこ"),
    POURQUOI("Pourquoi", "なぜ"),
    QUI("Qui", "だれ"),
    QUOI("Quoi", "なに"),
    QUEL("Quel", "どれ"),
    QUEL_OBJET("Quel (objet)", "どの"),
    PARCE_QUE("Parce que", "から"),
    POISSON("Poisson", "魚"),
    MANGER("Manger", "食べます"),
    TOKIDOKI("De temps en temps", "ときどき"),
    PETIT_DEJ("Petit déjeuner", "あさごはん"),
    NOMIMASU("Boire", "飲みます"),
    CAFE("Café", "コオヒイ"),
    BIERE("Bière", "ビイル"),
    VIN("Vin", "ワイン"),
    AIMER("Aimer", "すきです"),
    LEGUMES("Légumes", "やさい"),
    VIANDE("Viande", "肉"),
    FRUIS("Fruits", "くだもの"),
    PAS_DU_TOUT("Pas du tout", "ぜんぜん"),
    AMARI("Pas souvent", "あまり"),
    YOKU("Souvent", "よく"),
    ITSUMO("Toujours", "いつも"),
    TAMAGO("Oeuf", "卵"),
    PAN("Pain", "パン"),
    MISOSHIRU("Soupe miso", "みそしる"),
    MILUKU("Lait (katakana)", "ミルク"),
    MIZU("Eau", "水"),
    RAMEN("Ramen", "ラアメン"),
    YASUI("Pas cher", "やすい"),
    HAYAI("Rapide", "はやい"),
    OSOI("Lent", "おそい"),
    OISHII("Délicieux", "おいしい"),
    MAZUI("Mauvais (dégoutant)", "まずい"),
    KAWAII("Mignon", "かわいい"),
    TAKAI("Cher / Grand", "たかい"),
    GENKINA("Joyeux", "げんきな"),
    SUKINA("Aimé / Ce que j'aime le plus", "すきな"),
    KIRAINA("Détesté / Ce que j'aime le moins", "きらいな"),
    KIREINA("Beau / propre", "きれいな"),
    DEMO("Mais", "でも"),
    KORE("Cela", "これ"),
    SOSHITE("Ensuite / puis", "そして"),
    SOODESUKA("Ok, d'accord", "そうですか"),
    KINOO("Hier", "きのう"),
    ASHITA("Demain", "あした"),
    WAKARIMASU("Je sais / Je comprend", "わかります"),
    */
    GYUUNYUU("Lait (hiragana)", "ぎゅうにゅう"),

    //


    ;

    String fr;
    List<CharJP> japonais = new ArrayList<>();

    Vocabulaire(String fr, String jp) {
        this.fr = fr;

        for (int i = 0; i < jp.length(); i++) {
            char c1 = jp.charAt(i);
            Character c2 = (i + 1) >= jp.length() ? null : jp.charAt(i + 1);
            CharJP charJp;
            System.out.println("oui");
            System.out.println(String.valueOf(c1));

            if (c2 != null) {
                System.out.println("" + c1 + "" + c2);
                charJp = CombinaisonHiragana.parse(c1, c2);
                if (charJp != null) {
                    System.out.println("Parfait");
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
    }

    public boolean equals(String s) {
        boolean roomaji = false;
        Pattern pattern = Pattern.compile("([a-zA-Z])");
        Matcher matcher = pattern.matcher(String.valueOf(s.charAt(0)));
        if(matcher.find()) {
            roomaji = true;
        }
        int i = 0;
        for (CharJP charJP : this.japonais) {

            if (roomaji) {
                /*
                 * Cas où la personne écrit en roomaji
                 */

                String r = charJP.roomaji();
                int len = r.length();
                System.out.println(r);
                if (s.length() < i + len - 1) return false;

                StringBuilder test = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    test.append(s.charAt(i + j));
                }
                i += len;
                if (!test.toString().equalsIgnoreCase(charJP.roomaji())) return false;

            } else {
                /*
                 * Cas où la personne
                 */

                /* TODO cas où la personne écrit sans kanji (ou mixte)
                if (charJP instanceof Kanji) {
                    Kanji.parse(s.charAt(i))
                } else {
                }
                */

                if (charJP instanceof Combinaison) {
                    char c1 = s.charAt(i);
                    Character c2 = (i + 1) >= s.length() ? null : s.charAt(i + 1);

                    if (c2 == null) return false;
                    if (!("" + c1 + c2).equalsIgnoreCase(charJP.japonais())) return false;
                    i++;

                } else {
                    if (!String.valueOf(s.charAt(i)).equalsIgnoreCase(charJP.japonais())) return false;
                }


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
