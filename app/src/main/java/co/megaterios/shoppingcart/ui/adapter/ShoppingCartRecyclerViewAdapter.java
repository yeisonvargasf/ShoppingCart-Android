package co.megaterios.shoppingcart.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.megaterios.shoppingcart.R;
import co.megaterios.shoppingcart.domain.OrderProduct;

/**
 * Created by yeison on 18/03/17.
 */

public class ShoppingCartRecyclerViewAdapter extends
        RecyclerView.Adapter<ShoppingCartRecyclerViewAdapter.ProductViewHolder> {

    private static final String TAG = "ShoppingCartRecyclerViewAdapter";
    private ArrayList<OrderProduct> mProducts;
    private Context context;


    public ShoppingCartRecyclerViewAdapter(Context context) {
        this.mProducts = new ArrayList<OrderProduct>();
        this.context = context;
    }


    @Override
    public ShoppingCartRecyclerViewAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Return the item view inflate for Recycler View.
        ShoppingCartRecyclerViewAdapter.ProductViewHolder viewHolder = new ShoppingCartRecyclerViewAdapter.ProductViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_shopping_cart_product, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShoppingCartRecyclerViewAdapter.ProductViewHolder holder, int position) {

        final OrderProduct boundOrderProduct = this.mProducts.get(position);

        holder.vName.setText(boundOrderProduct.getMyProduct().getName());
        holder.vPrice.setText(String.valueOf(boundOrderProduct.getMyProduct().getPrice()));
        holder.vQuantity.setText(String.valueOf(boundOrderProduct.getQuantityCurrentOrder()));

        holder.vDelete.setOnClickListener( new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ((ShoppingCartRecyclerViewAdapter
                                                        .AdapterShoppingCartInteractionListener)
                                                        context)
                                                        .onDeleteProduct(boundOrderProduct.getMyProduct().getId());
                                            }
                                        }

        );
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    public void addAll(@NonNull ArrayList<OrderProduct> extraProducts) {
        this.mProducts.clear();
        this.mProducts.addAll(extraProducts);
        notifyDataSetChanged();
    }

    public int removeItem(OrderProduct soldOutProduct) {
        int i = this.mProducts.indexOf(soldOutProduct);
        this.mProducts.remove(soldOutProduct);
        notifyDataSetChanged();

        return i;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vPrice;
        protected TextView vQuantity;
        protected Button vDelete;

        public ProductViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.text_view_name_product);
            vPrice = (TextView)  v.findViewById(R.id.text_view_price_product);
            vQuantity = (TextView)  v.findViewById(R.id.text_view_added_product);
            vDelete = (Button) v.findViewById(R.id.button_delete_product);
        }

    }

    public interface AdapterShoppingCartInteractionListener {
        public void onDeleteProduct(String productId);
    }
}
