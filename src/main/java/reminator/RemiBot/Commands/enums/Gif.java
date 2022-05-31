package reminator.RemiBot.Commands.enums;

import java.util.Random;

public enum Gif {
    GIF1("https://tenor.com/view/ping-pong-ping-discord-anxious-nervous-gif-17839845"),
    GIF2("https://tenor.com/view/ayaya-yeah-happy-hapinness-kiniro-gif-12992329"),
    GIF3("https://images-ext-2.discordapp.net/external/IF5RQJpcOpBuXRzL54ARhYtoiM0vrtKoSox7WyK0AHI/https/cdn.weeb.sh/images/BymCu383W.gif"),
    GIF4("https://tenor.com/view/kawaii-anime-tongue-bleh-gif-5018411"),
    GIF5("https://images-ext-1.discordapp.net/external/9m4HWb3NmroBl8173dQ2U3_uw2pA4pAl8jd8cM2Laxk/https/imgur.com/ewty2xP.gif"),
    GIF6("https://images-ext-2.discordapp.net/external/ttTscMPIE1xoLEadp9uXaImr3ErnpciFHyyNfb78Sa4/https/imgur.com/x7Q4Xlp.gif"),
    GIF7("https://images-ext-2.discordapp.net/external/UlYfWGfOxDZYppTY8bI0JWgLHg_3C9A3fSMNodLHxNo/https/imgur.com/jjL2TrY.gif"),
    GIF8("https://tenor.com/view/creepy-tongue-weird-funny-gif-13480166"),
    GIF9("https://tenor.com/view/blends-anime-maika-gif-10176024"),
    GIF10("https://images-ext-1.discordapp.net/external/bKU_Xy3K4FXNsZK2qD6uaB8ATtuZggP6n6VCMz2KOHY/https/imgur.com/yRPUsv0.gif"),
    GIF11("https://images-ext-2.discordapp.net/external/wf-NvBhX86bcX52UWzxWEu3UlF3-h8xiv94hO58_Q_s/https/imgur.com/6qn3X2h.gif"),
    GIF12("https://images-ext-1.discordapp.net/external/3C2T8YAhfERTjkva3NNdnG0pHVgBRDDHomsSsThkq4k/https/imgur.com/ym2iyRx.gif"),
    GIF13("https://tenor.com/view/anime-hug-gif-18888736"),
    GIF14("https://tenor.com/view/anime-punch-wall-ouch-gif-9509158"),
    GIF15("https://tenor.com/view/bored-anime-boring-nothing-to-do-panda-gif-4583775"),
    GIF16("https://tenor.com/view/ahaha-wave-bye-fearless-im-out-gif-14503554"),
    GIF17("https://tenor.com/view/chi-chis-sweet-home-happy-bed-home-gif-5179902"),
    GIF18("https://tenor.com/view/cyanide-and-happiness-im-home-animation-strip-gif-11292245"),
    ;

    private final String url;

    Gif(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static Gif getRandom() {
        Random rd = new Random();
        return values()[rd.nextInt(values().length)];
    }
}
