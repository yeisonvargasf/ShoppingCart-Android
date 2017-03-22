package co.megaterios.shoppingcart.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.megaterios.shoppingcart.domain.Product;

/**
 * Created by yeison on 21/03/17.
 */

public class ProductsResponse {

    @SerializedName("results")
    private ArrayList<Product> mProducts;

    public ArrayList<Product> getProducts() {
        return mProducts;
    }
}
