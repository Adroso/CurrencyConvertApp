package com.example.adroso360.currencyconvert;
/**
 * Created by Adroso360 on 11/3/17
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class mainScreen extends AppCompatActivity {
    public static String convertingCountry;
    private EditText awayAmount;
    private TextView awayCurrency;
    private TextView statusText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        /** =======Initial App Set Up Section======= */
        SharedPreferences sharedBackground = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int bg = sharedBackground.getInt("background", Color.WHITE);
        View mainScreenView = findViewById(R.id.activity_main_screen);
        mainScreenView.setBackgroundColor(bg);

        //Allows HTTP requests from Android + Auth in Manifest.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Updates the currency rates upon opening of activity.
        FetchRate.updateRates();

        //Finding all UI elements
        EditText homeAmount = (EditText) findViewById(R.id.homeAmount);
        awayAmount = (EditText)findViewById(R.id.awayAmount);
        statusText = (TextView)findViewById(R.id.statusText);
        TextView dateStatus = (TextView) findViewById(R.id.dateStatus);
        awayCurrency = (TextView)findViewById(R.id.awayCurrency);
        Button settingsButt = (Button) findViewById(R.id.settingsButt);

        //App Logic Initial Setup
        if (Objects.equals(convertingCountry, null)){
            convertingCountry = "USD";
            updateRateText(convertingCountry, Currency.getInstance(convertingCountry).getSymbol(Locale.US));

        } else if(Objects.equals(convertingCountry, "NZD")){
            //This is to prevent getSymbol() from Printing "NZ$" instead of the required "$"
            updateRateText(convertingCountry, "$");
        } else{
            updateRateText(convertingCountry, Currency.getInstance(convertingCountry).getSymbol(Locale.US));
        }

        statusText.setText("Awaiting Amount");
        String dateText = ("Rates Updated: " + FetchRate.getRefreshDate());
        dateStatus.setText(dateText);

        /** =======END Initial App Set Up Section======= */

        /** =======Listener + logic Section======= */
        // Listener for Home Currency

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
                    awayAmount.setText("");
                    //Catches empty textField Error.
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Listener for Away Currency
        //TODO If time permits make conversion backwards compadible
        awayAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence awayS, int start, int before, int count) {

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
    private void updateRateText(String currencyName, String currencySymbol){
        awayCurrency.setText(currencyName + " " + currencySymbol);
    }

    /** =======END Listener + logic Section======= */
}
