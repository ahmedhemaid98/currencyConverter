package com.example.currencyconverter.view;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.currencyconverter.R;
import com.example.currencyconverter.model.Rates;
import com.example.currencyconverter.model.RequestResult;
import com.example.currencyconverter.viewmodel.MainActivityViewModel;


import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class MainActivity extends CommonActivity {
    private Spinner spinner1;
    private Spinner spinner2;
    private ArrayList<Rates> rates;
    private String selectedCurrency1 = "";
    private String selectedCurrency2 = "";
    private ImageView flag1,flag2;
    private MainActivityViewModel viewModel;
    private TextView symbol,symbol2;
    private ArrayList<String> namesArr = new ArrayList<>();
    private ArrayList<String> currenciesNum = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText currency1;
    private TextView currency2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, new MainActivityViewModel.ClientViewModelFactory(this)).get(MainActivityViewModel.class);

        initUI();
        rates = new ArrayList<>();
        spinnersOperations();
        initRates();
        editTextWatcher();

    }
    private void initUI() {
        flag1=findViewById(R.id.imageView);
        flag2=findViewById(R.id.imageView2);
        symbol = findViewById(R.id.symbol);
        symbol2 = findViewById(R.id.symbol2);
        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
    }
    private void editTextWatcher() {
        currency1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int num = Integer.parseInt(s.toString());
                    currency2.setText(exchange(num) + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void spinnersOperations() {
        adapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, namesArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrency1 = adapterView.getItemAtPosition(i).toString();
                String s = currency1.getText().toString();
                if (!s.isEmpty()) {
                    int num = Integer.parseInt(s);
                    currency2.setText(exchange(num) + "");
                }
                Glide.with(flag1).load("https://www.countryflags.io/"+selectedCurrency1.substring(0,2)+"/shiny/64.png").into(flag1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrency2 = adapterView.getItemAtPosition(i).toString();
                String s = currency1.getText().toString();
                if (!s.isEmpty()) {
                    int num = Integer.parseInt(s);
                    currency2.setText(exchange(num) + "");
                }
                Glide.with(flag2).load("https://www.countryflags.io/"+selectedCurrency2.substring(0,2)+"/shiny/64.png").into(flag2);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private double exchange(int num) {
        int position1 = spinner1.getSelectedItemPosition();
        int position2 = spinner2.getSelectedItemPosition();
        Double cur1 = Double.parseDouble(currenciesNum.get(position1));
        Double cur2 = Double.parseDouble(currenciesNum.get(position2));
        Double result = cur2 / cur1 * num;

        Locale uk = new Locale("en", "GB");
        Currency pound = Currency.getInstance(namesArr.get(position1));
        symbol.setText(pound.getSymbol(uk));

        Currency pound2 = Currency.getInstance(namesArr.get(position2));
        symbol2.setText(pound2.getSymbol(uk));

        return result;
    }

    public void initRates() {
        viewModel.getCurrencies().observe(this, new Observer<RequestResult>() {
            @Override
            public void onChanged(RequestResult result) {

                if (result != null) {
                    String[] names = {};

                    Rates ratesResult = result.getRates();


                    names = ratesResult.toString().split(",");
                    for (int i = 0; i < names.length; i++) {

                        String[] s = {};
                        s = names[i].split("=");
                        System.out.println(s[0]);
                        namesArr.add(s[0]);
                        currenciesNum.add(s[1]);
                    }
                    adapter.notifyDataSetChanged();

                    if (rates != null) {
                        rates.add(ratesResult);
                    }
                }
            }
        });

    }

    public void change(View view) {
        int newPos1 = spinner2.getSelectedItemPosition();
        int newPos2 = spinner1.getSelectedItemPosition();
        spinner1.setSelection(newPos1);
        spinner2.setSelection(newPos2);
        String s = currency1.getText().toString();
        if (!s.isEmpty()) {
            int num = Integer.parseInt(s);
            currency2.setText(exchange(num) + "");
        }

    }
}