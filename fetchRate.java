package com.example.adroso360.currencyconvert;

/**
 * Created by Adroso360 on 11/3/17
 */

import android.net.NetworkRequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FetchRate {
    public static JsonObject conversionRates;
    public static String updateTime;

    public static void main(String[] args) {

    }
    public static JsonObject updateRates() {
        // This pings the Fixer Currency Converter API and returns a JSON of all currency conversion rates for the Base currency submitted.
        //JsonObject conversionRates = null;


        try {
            URL fixerAPI = new URL("http://api.fixer.io/latest?base=AUD");
            URLConnection urlConnect = fixerAPI.openConnection();
            InputStream is = urlConnect.getInputStream();

            //Parsing API in as string
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String stringRates = s.hasNext() ? s.next() : "";
            is.close();


            //Converting string to JsonObject
            JsonParser parser = new JsonParser();
            conversionRates = parser.parse(stringRates).getAsJsonObject();

            DateFormat df = new SimpleDateFormat("h:mm a");
            updateTime = df.format(Calendar.getInstance().getTime());



        } catch (IOException e) {
            e.printStackTrace();
        }

        return conversionRates;
    }

    public static double chooseRate(String selectedRate){
        JsonObject listRates = conversionRates.get("rates").getAsJsonObject();
        return listRates.get(selectedRate).getAsDouble();

    }

    public static String getRefreshDate(){
        return conversionRates.get("date").getAsString() + " " + updateTime;

    }



}

