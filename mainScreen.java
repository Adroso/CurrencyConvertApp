package com.example.adroso360.currencyconvert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.StrictMode;

import java.util.Objects;

public class mainScreen extends AppCompatActivity {

    public static String convertingCountry;
    private EditText homeAmount;
    private EditText awayAmount;
    private TextView statusText;
    private TextView dateStatus;
    private TextView awayCurrency;
    private Button settingsButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //allows HTTP requests
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Updates the currency rates upon open.
        FetchRate.updateRates();

        homeAmount = (EditText)findViewById(R.id.homeAmount);
        awayAmount = (EditText)findViewById(R.id.awayAmount);
        statusText = (TextView)findViewById(R.id.statusText);
        dateStatus = (TextView)findViewById(R.id.dateStatus);
        awayCurrency = (TextView)findViewById(R.id.awayCurrency);
        settingsButt = (Button)findViewById(R.id.settingsButt);

        //App Setup

        if (Objects.equals(convertingCountry, null)){
            convertingCountry = "USD";
        } else {
            awayCurrency.setText(convertingCountry +" $");
        }

        statusText.setText("Awaiting Amount");
        String dateText = ("Rates Updated: " + FetchRate.getRefreshDate());
        dateStatus.setText(dateText);


        // Listener for home Currency

        homeAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                statusText.setText("Converting AUD to " + awayCurrency.getText());

                try {
                    double homeDouble = Double.parseDouble(s.toString());
                    String convertedAmount = Double.toString(Converter.convertCurrency(homeDouble, FetchRate.chooseRate(convertingCountry)));
                    awayAmount.setText(convertedAmount);
                } catch (Exception e){
                    //error
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Listener for Away Currency
        awayAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//               statusText.setText("Converting " + awayCurrency.getText() + " to AUD $");
//
//                try {
//                    double awayDouble = Double.parseDouble(s.toString());
//                    String convertedAmountAway = Double.toString(Converter.convertCurrencyReverse(awayDouble, FetchRate.chooseRate("USD")));
//                    homeAmount.setText(convertedAmountAway);
//                } catch (Exception e){
//                    //error
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        settingsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainScreen.this, SettingsScreen.class));
            }
        });

    }
}
