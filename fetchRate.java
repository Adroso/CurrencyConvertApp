package com.example.adroso360.currencyconvert;

/**
 * Created by Adroso360 on 11/3/17
 */

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

        /** This pings the Fixer Currency Converter API and returns a JSON of all currency
         * conversion rates for the Base currency submitted. For simplicity sake this will be left as AUD
         * However by adding a URL builder and user input the converter can be for anything.
        */

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
            //Raised if there is an error retrieving data from the input stream
        }

        return conversionRates;
    }

    public static double chooseRate(String selectedRate){
        /** This function takes a string of the currencyCode in and returns
         *  the matching conversion rate in the JSON File.
         */
        JsonObject listRates = conversionRates.get("rates").getAsJsonObject();
        return listRates.get(selectedRate).getAsDouble();

    }

    public static String getRefreshDate(){
        /** Used to update the status text to when the Currency Rate JSON was last updated.
         */
        return conversionRates.get("date").getAsString() + " " + updateTime;

    }



}

