package co.megaterios.shoppingcart.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import co.megaterios.shoppingcart.R;
import co.megaterios.shoppingcart.domain.Order;
import co.megaterios.shoppingcart.service.ShoppingCartApiAdapter;
import co.megaterios.shoppingcart.ui.adapter.ShoppingCartRecyclerViewAdapter;
import co.megaterios.shoppingcart.util.Helpers;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShoppingCartActivity extends AppCompatActivity implements
        ShoppingCartRecyclerViewAdapter.AdapterShoppingCartInteractionListener, Callback<Response> {

    private static final String TAG = "ShoppingCartActivity";
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
        ShoppingCartApiAdapter.getApiService().getOrder(ListProductsActivity.ORDER_ID, this);

        mRecList.setAdapter(mListShoppingCartProductsAdapter);
    }

    @Override
    public void onDeleteProduct(String orderProductId) {
        ShoppingCartApiAdapter.getApiService().removeProductFromOrder(ListProductsActivity.ORDER_ID,
                orderProductId, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.i(TAG, "Success removing the product.");
                        ShoppingCartApiAdapter.getApiService().getOrder(
                                ListProductsActivity.ORDER_ID, ShoppingCartActivity.this);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                "¡Sin conexión a Internet!, vuelve a intentarlo.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void success(Response response, Response response2) {
        Order currentOrder = new Gson().fromJson(Helpers.getResponseBody(response), Order.class);

        mListShoppingCartProductsAdapter.addAll(currentOrder.getProducts());
    }



    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getApplicationContext(),
                "¡Sin conexión a Internet!, vuelve a intentarlo.",
                Toast.LENGTH_SHORT).show();
    }
}
