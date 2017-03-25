package com.example.adroso360.currencyconvert;

/**
 * Created by Adroso360 on 11/3/17
 */
class Converter {

    public static double convertCurrency(double homeCurrency, double conversionRate){
        /**
         * Simple Currency Conversion with Rounding to 2 decimal Places
         */

        return Math.round((homeCurrency * conversionRate)*100.0)/100.0;
    }

    public static double convertCurrencyReverse(double homeCurrency, double conversionRate){
        /**
         * Simple Reverse Currency Conversion with Rounding to 2 decimal Places
         */

        return Math.round((homeCurrency / conversionRate)*100.0)/100.0;
    }
}
