package co.megaterios.shoppingcart.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.megaterios.shoppingcart.R;
import co.megaterios.shoppingcart.domain.OrderProduct;
import co.megaterios.shoppingcart.domain.Stock;
import co.megaterios.shoppingcart.ui.adapter.ShoppingCartRecyclerViewAdapter;

public class ShoppingCartActivity extends AppCompatActivity implements
        ShoppingCartRecyclerViewAdapter.AdapterShoppingCartInteractionListener {

    private RecyclerView mRecList;
    private ShoppingCartRecyclerViewAdapter mListShoppingCartProductsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        this.mListShoppingCartProductsAdapter = new ShoppingCartRecyclerViewAdapter(this);

        mRecList = (RecyclerView) findViewById(R.id.recycler_view_list_products_shopping_cart);
        mRecList.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);

        // Load data here! :)
        mListShoppingCartProductsAdapter.addAll(new ArrayList<OrderProduct>(
                OrderProduct.find(OrderProduct.class, "order_Id = ?", ListProductsActivity.ORDER_ID)
        ));

        mRecList.setAdapter(mListShoppingCartProductsAdapter);
    }

    @Override
    public void onDeleteProduct(Long orderProductId) {
        OrderProduct deletedOrderProduct = OrderProduct.findById(OrderProduct.class, orderProductId);

        List<Stock> stocks = Stock.find(Stock.class, "product = ?",
                String.valueOf(deletedOrderProduct.getCoolProduct().getId()));
        if (stocks == null || stocks.size() < 1) {
            Toast.makeText(getApplicationContext(), "Error: Can not delete this product.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Stock currentStock = stocks.get(0);

        currentStock.setQuantity(currentStock.getQuantity() + 1);
        currentStock.save();

        deletedOrderProduct.setOrderProductQuantity(deletedOrderProduct.getOrderProductQuantity() - 1);
        deletedOrderProduct.save();

        if(deletedOrderProduct.getOrderProductQuantity() == 0) {
            mListShoppingCartProductsAdapter.removeItem(deletedOrderProduct);
            deletedOrderProduct.delete();
        }

        Toast.makeText(getApplicationContext(), deletedOrderProduct.getCoolProduct().getName()  + " deleted.",
                Toast.LENGTH_SHORT).show();
    }
}
