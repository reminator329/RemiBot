package reminator.RemiBot.Commands.Japonais.enums;

public enum PetitHiragana implements CharJP {
    p_ya('ゃ'), p_yu('ゅ'), p_yo('ょ')
    ;

    char hiragana;

    PetitHiragana(char hiragana) {
        this.hiragana = hiragana;
    }

    @Override
    public String roomaji() {
        return this.name();
    }

    @Override
    public String japonais() {
        return "" + this.hiragana;
    }

    public static PetitHiragana parse(char c) {
        for (PetitHiragana h : PetitHiragana.values()) {
            if (h.japonais().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }
}
