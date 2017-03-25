package com.example.adroso360.currencyconvert;

/**
 * Created by Adroso360 on 11/3/17
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


        } catch (Exception e) {
            //Raised if there is an error retrieving data from the input stream
            e.printStackTrace();
            JsonParser parserException = new JsonParser();
            String backupRates = "{\"base\":\"AUD\",\"date\":\"2017-03-22\",\"rates\":{\"BGN\":1.3885,\"BRL\":2.3771,\"CAD\":1.0264,\"CHF\":0.76061,\"CNY\":5.2842,\"CZK\":19.183,\"DKK\":5.2799,\"GBP\":0.61614,\"HKD\":5.9597,\"HRK\":5.2597,\"HUF\":219.48,\"IDR\":10226.0,\"ILS\":2.8043,\"INR\":50.192,\"JPY\":85.241,\"KRW\":859.08,\"MXN\":14.688,\"MYR\":3.3968,\"NOK\":6.4932,\"NZD\":1.0899,\"PHP\":38.529,\"PLN\":3.0396,\"RON\":3.2365,\"RUB\":44.477,\"SEK\":6.7517,\"SGD\":1.0726,\"THB\":26.569,\"TRY\":2.7798,\"USD\":0.76722,\"ZAR\":9.6628,\"EUR\":0.70992}}";
            conversionRates = parserException.parse(backupRates).getAsJsonObject();
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
         * This also will add an error message when up to date rates could not be retrived due to
         * internet issues.
         */
        if (updateTime == null){
            updateTime = "ERROR RETRIEVING CURRENT RATES";
        }
        return conversionRates.get("date").getAsString() + " " + updateTime;

    }



}

