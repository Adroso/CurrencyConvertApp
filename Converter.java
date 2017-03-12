package com.example.adroso360.currencyconvert;

/**
 * Created by Adroso360 on 11/3/17.
 */
import java.text.DecimalFormat;
public class Converter {

    public static double convertCurrency(double homeCurrency, double conversionRate){

        return Math.round((homeCurrency * conversionRate)*100.0)/100.0;
    }

    public static double convertCurrencyReverse(double homeCurrency, double conversionRate){

        return Math.round((homeCurrency / conversionRate)*100.0)/100.0;
    }
}
