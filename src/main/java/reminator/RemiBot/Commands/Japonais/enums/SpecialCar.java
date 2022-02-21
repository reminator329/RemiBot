package reminator.RemiBot.Commands.Japonais.enums;

import reminator.RemiBot.Commands.Japonais.enums.kanas.PetitHiragana;

public enum SpecialCar implements CharJP {
    ALLONGEMENT_KATAKANA('ー');

    char car;

    SpecialCar(char car) {
        this.car = car;
    }

    public char getCar() {
        return car;
    }

    public static SpecialCar parse(char c) {
        for (SpecialCar h : SpecialCar.values()) {
            if (h.japonais().equalsIgnoreCase(String.valueOf(c))) {
                return h;
            }
        }
        return null;
    }

    @Override
    public String roomaji() {
        return this.name();
    }

    @Override
    public String japonais() {
        return "" + this.car;
    }
}
