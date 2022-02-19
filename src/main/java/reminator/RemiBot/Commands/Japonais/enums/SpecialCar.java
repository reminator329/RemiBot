package reminator.RemiBot.Commands.Japonais.enums;

public enum SpecialCar {
    ALLONGEMENT_KATAKANA('ー');

    char car;

    SpecialCar(char car) {
        this.car = car;
    }

    public char getCar() {
        return car;
    }
}
