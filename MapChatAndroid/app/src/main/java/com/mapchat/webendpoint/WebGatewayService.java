package com.mapchat.webendpoint;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * REST service for server communication
 *
 */

public final class WebGatewayService {
    public static String IP_ADDRESS = "192.168.0.11:54321";
    private static WebGatewayService instance;

    private WebGatewayInterface gatewayI;

    public WebGatewayService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + IP_ADDRESS)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gatewayI = retrofit.create(WebGatewayInterface.class);
    }

    /**
     *  creates a webservice instance
     * @return
     */

    public static WebGatewayService getInstance() {
        if (instance == null) {
            instance = new WebGatewayService();
        }
        return instance;
    }

    /**
     * returns a gateway service instance
     * @return
     */

    public WebGatewayInterface getGateWayService() {
        return gatewayI;
    }

}

