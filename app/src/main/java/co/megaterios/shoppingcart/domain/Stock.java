package co.megaterios.shoppingcart.domain;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by yeison on 18/03/17.
 */

public class Stock extends SugarRecord {

    @SerializedName("my_product_id")
    private String productId;
    private int quantity;

    public Stock() {

    }

    public Stock(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

        return getProductId().equals(stock.getProductId());

    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "product=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
