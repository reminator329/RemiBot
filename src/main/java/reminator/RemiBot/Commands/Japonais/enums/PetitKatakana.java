package reminator.RemiBot.Commands.Japonais.enums;

public enum PetitKatakana implements CharJP {
    p_A('ァ'), p_I('ィ'), p_U('ゥ'), p_E('ェ'), p_O('ォ'),

    p_YA('ャ'), p_YU('ュ'), p_YO('ョ')
    ;

    char hiragana;

    PetitKatakana(char hiragana) {
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

    public static PetitKatakana parse(char c) {
        for (PetitKatakana h : PetitKatakana.values()) {
            if (h.japonais().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }
}
