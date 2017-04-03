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
    private static JsonObject conversionRates;
    private static String updateTime;
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
            //Backup Rates to be used if the device has no internet connection
            String backupRates = "{\"base\":\"AUD\",\"date\":\"2017-03-31\",\"rates\":{\"BGN\":1.3988,\"BRL\":2.4174,\"CAD\":1.0202,\"CHF\":0.76498,\"CNY\":5.2669,\"CZK\":19.332,\"DKK\":5.3196,\"GBP\":0.61188,\"HKD\":5.9415,\"HRK\":5.3258,\"HUF\":220.01,\"IDR\":10183.0,\"ILS\":2.7788,\"INR\":49.632,\"JPY\":85.503,\"KRW\":854.31,\"MXN\":14.317,\"MYR\":3.3839,\"NOK\":6.5572,\"NZD\":1.0949,\"PHP\":38.376,\"PLN\":3.0228,\"RON\":3.256,\"RUB\":43.136,\"SEK\":6.8175,\"SGD\":1.0685,\"THB\":26.265,\"TRY\":2.7817,\"USD\":0.76463,\"ZAR\":10.185,\"EUR\":0.71521}}";
            conversionRates = parserException.parse(backupRates).getAsJsonObject();
        }

        return conversionRates;
    }


    static double chooseRate(String selectedRate){
        /** This function takes a string of the currencyCode in and returns
         *  the matching conversion rate in the JSON File.
         */
        JsonObject listRates = conversionRates.get("rates").getAsJsonObject();
        return listRates.get(selectedRate).getAsDouble();

    }

    static String getRefreshDate(){
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

