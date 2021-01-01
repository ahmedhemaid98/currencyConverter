package com.example.currencyconverter.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.currencyconverter.model.RequestResult;
import com.example.currencyconverter.model.network.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<RequestResult> resultLiveData;
    private NetworkUtils mNetworkUtils;

    public MainActivityViewModel(Context context) {
        mNetworkUtils = NetworkUtils.getInstance(context);
    }


    public LiveData<RequestResult> getCurrencies() {

        if (resultLiveData == null ) {
            resultLiveData = new MutableLiveData<>();


            mNetworkUtils.getCurrencyInterface().getCurrency()
                    .enqueue(new Callback<RequestResult>() {
                        @Override
                        public void onResponse(@NotNull Call<RequestResult> call, @NotNull Response<RequestResult> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                resultLiveData.setValue(response.body());
                                System.out.println(response.body());
                            } else {
                                //TODO: get data from database
                            }


                        }

                        @Override
                        public void onFailure(@NotNull Call<RequestResult> call, @NotNull Throwable t) {

                        }
                    });

        }
        return resultLiveData;

    }




    public void setMutableToNull(){
        resultLiveData = null;

    }


    public static class ClientViewModelFactory implements ViewModelProvider.Factory {

        private Context context;

        public ClientViewModelFactory(Context context) {
            this.context = context;
        }


        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainActivityViewModel(context);
        }
    }



}
