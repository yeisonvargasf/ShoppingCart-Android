package co.megaterios.shoppingcart.service;

import co.megaterios.shoppingcart.BuildConfig;
import retrofit.RestAdapter;

/**
 * Created by yeison on 21/03/17.
 */

public class ShoppingCartApiAdapter {

    private static ShoppingCartApiService apiService;

    public static ShoppingCartApiService getApiService() {

        if(apiService == null) {
            RestAdapter adapter =  new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.URL_API_HOST)
                    //.setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .build();

            apiService = adapter.create(ShoppingCartApiService.class);
        }

        return apiService;
    }
}
