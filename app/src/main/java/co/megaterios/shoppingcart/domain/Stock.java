package co.megaterios.shoppingcart.domain;

import com.orm.SugarRecord;

/**
 * Created by yeison on 18/03/17.
 */

public class Stock extends SugarRecord {

    private Product product;
    private int quantity;

    public Stock() {

    }

    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;

        Stock stock = (Stock) o;

        return getProduct().equals(stock.getProduct());

    }

    @Override
    public int hashCode() {
        return getProduct().hashCode();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
