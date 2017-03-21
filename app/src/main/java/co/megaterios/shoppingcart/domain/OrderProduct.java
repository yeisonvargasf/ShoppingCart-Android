package co.megaterios.shoppingcart.domain;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeison on 18/03/17.
 */

public class OrderProduct extends SugarRecord {
    
    private int orderId;
    private Product coolProduct;
    private int orderQuantity;

    public OrderProduct() {
    }

    public OrderProduct(int orderId, Product coolProduct, int orderQuantity) {
        this.orderId = orderId;
        this.coolProduct = coolProduct;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderProductQuantity() {
        return orderQuantity;
    }

    public void setOrderProductQuantity(int orderQuantity) {
        orderQuantity = orderQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getCoolProduct() {
        return coolProduct;
    }

    public void setCoolProduct(Product coolProduct) {
        this.coolProduct = coolProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct)) return false;

        OrderProduct order = (OrderProduct) o;

        return getId().equals(order.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                '}';
    }

    public int calculateQuantityByOrder(String orderId) {
        int quantity = 0;
        Log.d("OrderProduct", OrderProduct.find(OrderProduct.class, "order_Id = ?", orderId).size()
                + "");
        for(OrderProduct obj: OrderProduct.find(OrderProduct.class, "order_Id = ?", orderId)) {
            quantity += obj.getOrderProductQuantity();
        }
        Log.d("OrderProduct", quantity + "");
        return quantity;
    }

    public Double calculateTotalByOrder(String orderId) {
        Double total = 0.0;
        for(OrderProduct obj: OrderProduct.find(OrderProduct.class, "order_Id = ?", orderId)) {
            total += obj.getCoolProduct().getPrice() * obj.getOrderProductQuantity();
        }
        return total;
    }
}
