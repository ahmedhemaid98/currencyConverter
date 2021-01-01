package com.example.currencyconverter.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.currencyconverter.R;
import com.example.currencyconverter.model.Rates;
import com.example.currencyconverter.model.RequestResult;
import com.example.currencyconverter.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CommonActivity {
    private Spinner spinner1;
    private Spinner spinner2;
    private ArrayList<Rates> rates;
    private String selectedCurrency1 = "";
    private String selectedCurrency2 = "";
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, new MainActivityViewModel.ClientViewModelFactory(this)).get(MainActivityViewModel.class);
        rates = new ArrayList<>();
        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<Rates> adapter =
                new ArrayAdapter<Rates>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, rates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrency1 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2 = findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrency2 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getRates();
    }

    public ArrayList<Rates> getRates() {
        viewModel.getCurrencies().observe(this, new Observer<RequestResult>() {
            @Override
            public void onChanged(RequestResult result) {

                if (result != null) {

                    Rates ratesResult = result.getRates();
                    System.out.println(ratesResult.toString());
                    if (rates != null) {
                        rates.add(ratesResult);
                    }
                }
            }
        });
        return rates;
    }
}