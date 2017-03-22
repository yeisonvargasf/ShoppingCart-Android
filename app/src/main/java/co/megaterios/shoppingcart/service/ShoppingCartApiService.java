package co.megaterios.shoppingcart.service;

import co.megaterios.shoppingcart.service.model.ProductsResponse;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by yeison on 21/03/17.
 */

public interface ShoppingCartApiService {

    // 1. PUT /stock/id_product/ Quantity

    // 2. GET /products/ - OK
    @GET("/" + ApiConstants.API_VERSION + "/" + ApiConstants.PRODUCTS_ENDPOINT + "/?format=" +
            ApiConstants.FORMAT)
    void getProducts(Callback<ProductsResponse> serverResponse);

    // 3. GET /order/id_order/ - OK
    @GET("/" + ApiConstants.API_VERSION + "/" + ApiConstants.ORDERS_ENDPOINT + "/" + "{id}" +
            "/?format=" + ApiConstants.FORMAT)
    void getOrder(@Path("id") String id, Callback<Response> serverResponse);

    // 4. PUT /order/id_order/id_product add Product
    @FormUrlEncoded
    @POST("/" + ApiConstants.API_VERSION + "/" + ApiConstants.ORDERS_ENDPOINT + "/" + "{id}" +
            "/" + ApiConstants.ADD_PRODUCT_TO_ORDER + "/?format=" + ApiConstants.FORMAT)
    void addProductToOrder(@Path("id") String orderId,
                           @Field("product_id") String productId,
                           Callback<Response> serverResponse);


    @GET("/" + ApiConstants.API_VERSION + "/" + ApiConstants.ORDERS_ENDPOINT + "/" + "{id}" +
            "/" + ApiConstants.GET_ORDER_RESUME + "/?format=" + ApiConstants.FORMAT)
    void getOrderResume(@Path("id") String orderId, Callback<Response> serverResponse);




    // 5. PUT /order/id_order/id_product remove Product
    @FormUrlEncoded
    @POST("/" + ApiConstants.API_VERSION + "/" + ApiConstants.ORDERS_ENDPOINT + "/" + "{id}" +
            "/" + ApiConstants.REMOVE_PRODUCT_FROM_ORDER + "/?format=" + ApiConstants.FORMAT)
    void removeProductFromOrder(@Path("id") String orderId, @Field("product_id") String productId,
                           Callback<Response> serverResponse);



}
