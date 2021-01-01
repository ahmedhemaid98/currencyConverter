package com.example.currencyconverter.model.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private final String BASE_URL = "https://api.exchangeratesapi.io";

    private CurrencyInterface currencyInterface;
    private static NetworkUtils instance;

    public static NetworkUtils getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtils(context.getApplicationContext());
        }
        return instance;
    }

    private NetworkUtils(Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL+"/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyInterface = retrofit.create(CurrencyInterface.class);


    }

    public CurrencyInterface getCurrencyInterface() {
        return currencyInterface;
    }

}
