package reminator.RemiBot.Services.pricescanner.update;

import reminator.RemiBot.Services.pricescanner.Product;

import java.awt.*;

public class ProductStockUpdate implements ProductUpdate {
    private final Product product;
    private final boolean previous;
    private final boolean next;

    public ProductStockUpdate(Product product, boolean previous, boolean next) {
        this.product = product;
        this.previous = previous;
        this.next = next;
    }

    @Override
    public boolean hasChange() {
        return previous != next;
    }

    @Override
    public String getMessage() {
        if (!previous && next) {
            return"Le " + product + " est en stock ! :partying_face: \n" + product.url;
        } else if (previous && !next) {
            return "Le " + product + " n'est plus en stock ! :frowning: \n" + product.url;
        } else {
            return "Le " + product + " n'a pas changé de stock :neutral_face:";
        }
    }

    @Override
    public Color getColor() {
        if (!previous && next) {
            return Color.GREEN;
        } else if (previous && !next) {
            return Color.RED;
        } else {
            return Color.GRAY;
        }
    }
}
