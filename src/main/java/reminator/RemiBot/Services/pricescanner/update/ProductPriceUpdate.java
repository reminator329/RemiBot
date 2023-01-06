package reminator.RemiBot.Services.pricescanner.update;

import reminator.RemiBot.Services.pricescanner.Product;

import java.awt.*;

public class ProductPriceUpdate implements ProductUpdate {
    private final Product product;
    private final float previous;
    private final float next;

    public ProductPriceUpdate(Product product, float previous, float next) {
        this.product = product;
        this.previous = previous;
        this.next = next;
    }

    @Override
    public boolean hasChange() {
        // Check that difference between previous and next price is more than 5%
        return Math.abs(previous - next) / previous > 0.05;
    }

    @Override
    public String getMessage() {
        if (next < previous) {
            return "Le " + product + " a baissé de prix !! Il est passé de " + previous + "€ à " + next + "€ !! :partying_face:\n" + product.url;
        } else if (next > previous) {
            return "Le " + product + " a monté de prix ! Il est passé de " + previous + "€ à " + next + "€ ! :frowning:\n" + product.url;
        } else {
            return "Le " + product + " n'a pas changé de prix :neutral_face:";
        }
    }

    @Override
    public Color getColor() {
        if (next < previous) {
            return Color.GREEN;
        } else if (next > previous) {
            return Color.RED;
        } else {
            return Color.GRAY;
        }
    }
}
