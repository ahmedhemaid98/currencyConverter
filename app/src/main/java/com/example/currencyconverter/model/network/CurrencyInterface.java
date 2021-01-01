package com.example.currencyconverter.model.network;

import com.example.currencyconverter.model.RequestResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;


public interface CurrencyInterface {

    String LATEST = "latest";



    @GET(LATEST)
    Call<RequestResult> getCurrency();
}
