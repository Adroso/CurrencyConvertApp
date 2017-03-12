package com.example.adroso360.currencyconvert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class SettingsScreen extends AppCompatActivity {
    private Button mainButton;
    private Button rateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        mainButton = (Button)findViewById(R.id.mainButton);
        rateUpdate = (Button)findViewById(R.id.rateUpdate);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsScreen.this, mainScreen.class));
            }
        });
        rateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchRate.updateRates();
            }
        });

        //Handler for the Spinner of Country Choices

        final Spinner countrySpinner = (Spinner) findViewById(R.id.countryID);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countryChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainScreen.convertingCountry = countrySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
