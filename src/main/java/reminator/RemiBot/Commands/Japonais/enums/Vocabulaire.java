package reminator.RemiBot.Commands.Japonais.enums;

import reminator.RemiBot.Commands.Japonais.VocabulaireCommand;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Vocabulaire {

    // SEMESTRE 7 SRI

    ITSU("Quand", "いつ", Set.of(new Categorie[]{Categorie.S7})),
    DOKO("Où", "どこ", Set.of(new Categorie[]{Categorie.S7})),
    NAZE("Pourquoi", "なぜ", Set.of(new Categorie[]{Categorie.S7})),
    DARE("Qui", "だれ", Set.of(new Categorie[]{Categorie.S7})),
    NANI("Quoi", "なに", Set.of(new Categorie[]{Categorie.S7})),
    DORE("Quel", "どれ", Set.of(new Categorie[]{Categorie.S7})),
    DONO("Quel (objet)", "どの", Set.of(new Categorie[]{Categorie.S7})),
    KARA("Parce que", "から", Set.of(new Categorie[]{Categorie.S7})),
    SAKANA("Poisson", "魚", Set.of(new Categorie[]{Categorie.S7})),
    TABEMASU("Manger", "食べます", Set.of(new Categorie[]{Categorie.S7})),
    TOKIDOKI("De temps en temps", "ときどき", Set.of(new Categorie[]{Categorie.S7})),
    ASAGOHAN("Petit déjeuner", "あさごはん", Set.of(new Categorie[]{Categorie.S7})),
    NOMIMASU("Boire", "飲みます", Set.of(new Categorie[]{Categorie.S7})),
    KOOHII("Café", "コオヒイ", Set.of(new Categorie[]{Categorie.S7})),
    BIIRU("Bière", "ビイル", Set.of(new Categorie[]{Categorie.S7})),
    WAIN("Vin", "ワイン", Set.of(new Categorie[]{Categorie.S7})),
    SUKIDESU("Aimer", "すきです", Set.of(new Categorie[]{Categorie.S7})),
    YASAI("Légumes", "やさい", Set.of(new Categorie[]{Categorie.S7})),
    NIKU("Viande", "肉", Set.of(new Categorie[]{Categorie.S7})),
    KUDAMONO("Fruits", "くだもの", Set.of(new Categorie[]{Categorie.S7})),
    ZENZEN("Pas du tout", "ぜんぜん", Set.of(new Categorie[]{Categorie.S7})),
    AMARI("Pas souvent", "あまり", Set.of(new Categorie[]{Categorie.S7})),
    YOKU("Souvent", "よく", Set.of(new Categorie[]{Categorie.S7})),
    ITSUMO("Toujours", "いつも", Set.of(new Categorie[]{Categorie.S7})),
    TAMAGO("Oeuf", "卵", Set.of(new Categorie[]{Categorie.S7})),
    PAN("Pain", "パン", Set.of(new Categorie[]{Categorie.S7})),
    MISOSHIRU("Soupe miso", "みそしる", Set.of(new Categorie[]{Categorie.S7})),
    MILUKU("Lait (katakana)", "ミルク", Set.of(new Categorie[]{Categorie.S7})),
    GYUUNYUU("Lait (hiragana)", "ぎゅうにゅう", Set.of(new Categorie[]{Categorie.S7})),
    MIZU("Eau", "水", Set.of(new Categorie[]{Categorie.S7})),
    RAMEN("Ramen", "ラアメン", Set.of(new Categorie[]{Categorie.S7})),
    YASUI("Pas cher", "やすい", Set.of(new Categorie[]{Categorie.S7})),
    HAYAI("Rapide", "はやい", Set.of(new Categorie[]{Categorie.S7})),
    OSOI("Lent", "おそい", Set.of(new Categorie[]{Categorie.S7})),
    OISHII("Délicieux", "おいしい", Set.of(new Categorie[]{Categorie.S7})),
    MAZUI("Mauvais (dégoutant)", "まずい", Set.of(new Categorie[]{Categorie.S7})),
    KAWAII("Mignon", "かわいい", Set.of(new Categorie[]{Categorie.S7})),
    TAKAI("Cher / Grand", "たかい", Set.of(new Categorie[]{Categorie.S7})),
    GENKINA("Joyeux", "げんきな", Set.of(new Categorie[]{Categorie.S7})),
    SUKINA("Aimé / Ce que j'aime le plus", "すきな", Set.of(new Categorie[]{Categorie.S7})),
    KIRAINA("Détesté / Ce que j'aime le moins", "きらいな", Set.of(new Categorie[]{Categorie.S7})),
    KIREINA("Beau / propre", "きれいな", Set.of(new Categorie[]{Categorie.S7})),
    DEMO("Mais", "でも", Set.of(new Categorie[]{Categorie.S7})),
    KORE("Cela", "これ", Set.of(new Categorie[]{Categorie.S7})),
    SOSHITE("Ensuite / puis", "そして", Set.of(new Categorie[]{Categorie.S7})),
    SOODESUKA("Ok, d'accord", "そうですか", Set.of(new Categorie[]{Categorie.S7})),
    KINOO("Hier", "きのう", Set.of(new Categorie[]{Categorie.S7})),
    ASHITA("Demain", "あした", Set.of(new Categorie[]{Categorie.S7})),
    WAKARIMASU("Je sais / Je comprend", "わかります", Set.of(new Categorie[]{Categorie.S7})),

    // SEMESTRE 8 SRI
    UE("Sur / au-dessus de", "うえ", Set.of(new Categorie[]{Categorie.S8, Categorie.POSITION})),


    ;

    String fr;
    List<CharJP> japonais = new ArrayList<>();
    Set<Categorie> categories;

    Vocabulaire(String fr, String jp, Set<Categorie> categories) {
        this.fr = fr;
        this.categories = categories;

        for (int i = 0; i < jp.length(); i++) {
            char c1 = jp.charAt(i);
            Character c2 = (i + 1) >= jp.length() ? null : jp.charAt(i + 1);
            CharJP charJp;

            if (c2 != null) {
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

    public static Vocabulaire getRandom(Set<String> cats) {
        if (cats == null) {
            return Vocabulaire.values()[new Random().nextInt(Vocabulaire.values().length)];
        }

        Set<Categorie> categories = new HashSet<>();
        List<Vocabulaire> vocabulaires = new ArrayList<>();

        for (String cat : cats) {
            Categorie categorie = Categorie.parse(cat);
            if (categorie != null) {
                categories.add(categorie);
            }
        }

        for (Vocabulaire vocabulaire : Vocabulaire.values()) {
            Set<Categorie> communs = new HashSet<>(categories);
            communs.retainAll(vocabulaire.categories);
            if (communs.size() != 0) {
                vocabulaires.add(vocabulaire);
            }
        }

        return vocabulaires.get(new Random().nextInt(vocabulaires.size()));
    }
}
