package co.megaterios.shoppingcart.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeison on 21/03/17.
 */

public class OrderProduct {

    @SerializedName("my_product")
    private Product myProduct;
    @SerializedName("quantity")
    private int quantityCurrentOrder;

    public OrderProduct() {
    }

    public Product getMyProduct() {
        return myProduct;
    }

    public void setMyProduct(Product myProduct) {
        this.myProduct = myProduct;
    }

    public int getQuantityCurrentOrder() {
        return quantityCurrentOrder;
    }

    public void setQuantityCurrentOrder(int quantityCurrentOrder) {
        this.quantityCurrentOrder = quantityCurrentOrder;
    }

}
