package co.megaterios.shoppingcart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import co.megaterios.shoppingcart.R;
import co.megaterios.shoppingcart.domain.OrderProduct;
import co.megaterios.shoppingcart.domain.Product;
import co.megaterios.shoppingcart.domain.Stock;
import co.megaterios.shoppingcart.ui.adapter.ListProductsRecyclerViewAdapter;

public class ListProductsActivity extends AppCompatActivity implements
        ListProductsRecyclerViewAdapter.AdapterInteractionListener {

    private RecyclerView mRecList;
    private ListProductsRecyclerViewAdapter mListProductAdapter = new
            ListProductsRecyclerViewAdapter(this);
    public static String ORDER_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        ((TextView)findViewById(R.id.text_view_right_counter))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent showShoppingCart = new Intent(getApplicationContext(),
                                ShoppingCartActivity.class);
                        startActivity(showShoppingCart);
                    }
                });

        mRecList = (RecyclerView) findViewById(R.id.recycler_view_list_products);
        mRecList.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecList.setLayoutManager(llm);

        // Load data here! :)

        ArrayList<Product> dummyProducts =
                new ArrayList<Product>(
                    Arrays.asList(
                            new Product("Galaxy", 55.00),
                            new Product("Galaxy", 56.00),
                            new Product("Galaxy", 57.00),
                            new Product("Galaxy", 58.00),
                            new Product("Galaxy", 59.00),
                            new Product("Galaxy", 60.00),
                            new Product("Galaxy", 12.00),
                            new Product("Galaxy", 23.00),
                            new Product("Galaxy", 54.00),
                            new Product("Galaxy", 25.00),
                            new Product("Galaxy", 15.00),
                            new Product("Galaxy", 5.00),
                            new Product("Galaxy", 56.00),
                            new Product("Galaxy", 44.00),
                            new Product("Galaxy", 22.00),
                            new Product("Galaxy", 55.00),
                            new Product("Galaxy", 55.00),
                            new Product("Galaxy", 55.00)
                    )
                );

        for(Product obj: dummyProducts) {
            obj.save();
            new Stock(obj, new Random().nextInt(20)).save();
        }

        mListProductAdapter.addAll(dummyProducts);

        mRecList.setAdapter(mListProductAdapter);
    }

    @Override
    public void onBuyProduct(Long productId) {

        Product boughtProduct = Product.findById(Product.class, productId);

        List<Stock> stocks = Stock.find(Stock.class, "product = ?",
                String.valueOf(boughtProduct.getId()));

        if (stocks == null || stocks.size() < 1 || stocks.get(0).getQuantity() < 1) {
            Toast.makeText(getApplicationContext(), "Error: Can not buy this product.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Stock currentStock = stocks.get(0);

        currentStock.setQuantity(currentStock.getQuantity() - 1);
        currentStock.save();


        List<OrderProduct> orderProducts = OrderProduct.find(OrderProduct.class, "order_Id = ? and cool_Product = ?",
                ListProductsActivity.ORDER_ID, String.valueOf(productId));

        if(orderProducts.size() > 1) {
            Toast.makeText(getApplicationContext(), "Error: Can not buy this product. Internal Error",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        OrderProduct currentOrderProduct;

        if(orderProducts.size() == 0) {
            currentOrderProduct = new OrderProduct(Integer.parseInt(ListProductsActivity.ORDER_ID),
                    boughtProduct, 0);
            currentOrderProduct.save();
        } else {
            currentOrderProduct = orderProducts.get(0);
        }

        currentOrderProduct.setOrderProductQuantity(currentOrderProduct.getOrderProductQuantity() + 1);
        currentOrderProduct.save();

        if(currentStock.getQuantity() == 0) {
            mListProductAdapter.removeItem(boughtProduct);
            Toast.makeText(getApplicationContext(), boughtProduct.getName() + " sold out.",
                    Toast.LENGTH_SHORT).show();
        }

        ((TextView)findViewById(R.id.text_view_right_counter))
                .setText(currentOrderProduct.calculateQuantityByOrder(ListProductsActivity.ORDER_ID)
                        + " - " + currentOrderProduct.calculateTotalByOrder(ListProductsActivity.ORDER_ID));
    }
}
