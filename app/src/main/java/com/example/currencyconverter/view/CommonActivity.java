package com.example.currencyconverter.view;

import androidx.appcompat.app.AppCompatActivity;

public class CommonActivity extends AppCompatActivity {


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO: cancel all requests
    }
}
