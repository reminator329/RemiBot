package reminator.RemiBot.Services.pricescanner;

import reminator.RemiBot.Services.pricescanner.update.ProductPriceUpdate;
import reminator.RemiBot.Services.pricescanner.update.ProductStockUpdate;
import reminator.RemiBot.Services.pricescanner.update.ProductUpdate;

import java.io.Serializable;

public class Product implements Serializable {
    public String name;
    public String url;
    public Float price;
    public boolean inStock;

    public Product(String name, String url, Float price, boolean inStock) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.inStock = inStock;
    }

    public ProductUpdate update(Product other) {
        ProductUpdate updateStock = updateInStock(other.inStock);
        ProductUpdate updatePrice = updatePrice(other.price);

        return updateStock.hasChange() ? updateStock : updatePrice;
    }

    public ProductPriceUpdate updatePrice(float newPrice) {
        ProductPriceUpdate update = new ProductPriceUpdate(this, price, newPrice);
        this.price = newPrice;
        return update;
    }

    public ProductUpdate updateInStock(boolean newInStock) {
        ProductStockUpdate update = new ProductStockUpdate(this, inStock, newInStock);
        this.inStock = newInStock;
        return update;
    }

    @Override
    public String toString() {
        return name;
    }
}
