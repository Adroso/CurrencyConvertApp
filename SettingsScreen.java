package com.example.adroso360.currencyconvert;
/**
 * Created by Adroso360 on 11/3/17
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.Context;

public class SettingsScreen extends AppCompatActivity {
    private Button mainButton;
    private Button rateUpdate;
    private Button whiteBgButton;
    private Button greyBgButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        /** ======= Settings Activity Initial Setup Section ======= */
        mainButton = (Button)findViewById(R.id.mainButton);
        rateUpdate = (Button)findViewById(R.id.rateUpdate);
       whiteBgButton = (Button)findViewById(R.id.whiteBgButton);
       greyBgButton = (Button)findViewById(R.id.greyBgButton);

        final SharedPreferences sharedBackground = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int bg = sharedBackground.getInt("background", Color.WHITE);
        View settingsScreenView = findViewById(R.id.activity_settings_screen);
        settingsScreenView.setBackgroundColor(bg);

        /** ======= END Settings Activity Initial Setup Section ======= */

        /** ======= Listener + Logic Section ======= */
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

        whiteBgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedBackground.edit();
                editor.putInt("background", Color.WHITE);
                editor.apply();
                View vs = findViewById(R.id.activity_settings_screen);
                vs.setBackgroundColor(Color.WHITE);
            }
        });

        greyBgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedBackground.edit();
                editor.putInt("background", Color.LTGRAY);
                editor.apply();
                View vs = findViewById(R.id.activity_settings_screen);
                vs.setBackgroundColor(Color.LTGRAY);
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

        /** ======= END Listener + Logic Section ======= */

        });
    }
}
