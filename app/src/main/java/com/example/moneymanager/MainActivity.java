package com.example.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //SharedPreferences
    private String sharedPrefFile = "com.example.android.MoneyManager.FinancialGoals";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor preferencesEditor;
    //Purpose: List of public variables
    public LinkedList<String> itemNames = new LinkedList<>();
    public LinkedList<String> itemPrices = new LinkedList<>();
    public LinkedList<String> itemDates = new LinkedList<>();
    public String itemNamesLong;
    public String itemPricesLong;
    public String itemDatesLong;
    public float totalSum;
    public String totalSumString;
    public TextView mDisplayTotalSum;


    //Purpose: onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        DatePicker spinnerDate = findViewById(R.id.datePickerSpinner);
        spinnerDate.setMaxDate(new Date().getTime());

        itemNamesLong = mPreferences.getString("namesKey", itemNamesLong);
        itemPricesLong = mPreferences.getString("pricesKey", itemPricesLong);
        itemDatesLong = mPreferences.getString("datesKey", itemDatesLong);


        totalSum = mPreferences.getFloat("totalSumKey", totalSum);
        totalSumString = mPreferences.getString("totalSumStringKey", totalSumString);


        mDisplayTotalSum = findViewById(R.id.displayTotalSum);
        mDisplayTotalSum.setText("Money Saved: $" + totalSum);


        if (itemNamesLong != null && itemPricesLong != null && itemDatesLong != null) {

            itemNamesLong = itemNamesLong.substring(1, itemNamesLong.length() - 1);
            itemPricesLong = itemPricesLong.substring(1, itemPricesLong.length() - 1);
            itemDatesLong = itemDatesLong.substring(1, itemDatesLong.length()-1);
            String[] nameArray = itemNamesLong.split(", ");
            String[] priceArray = itemPricesLong.split(", ");
            String[] datesArray = itemDatesLong.split(", ");
            itemNames = new LinkedList<>(Arrays.asList(nameArray));
            itemPrices = new LinkedList<>(Arrays.asList(priceArray));
            itemDates = new LinkedList<>(Arrays.asList(datesArray));
        }
    }

    //Purpose: Returns correct totalSum from FinancialGoals
    @Override
    protected void onRestart() {
        super.onRestart();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        DatePicker spinnerDate = findViewById(R.id.datePickerSpinner);
        spinnerDate.setMaxDate(new Date().getTime());

        mPreferences.getString("namesKey", itemNamesLong);
        mPreferences.getString("pricesKey", itemPricesLong);
        mPreferences.getString("datesKey", itemDatesLong);


        totalSum = mPreferences.getFloat("totalSumKey", totalSum);
        totalSumString = mPreferences.getString("totalSumStringKey", totalSumString);

        mDisplayTotalSum = findViewById(R.id.displayTotalSum);
        mDisplayTotalSum.setText("Money Saved: $" + totalSum);


        if (itemNamesLong != null && itemPricesLong != null && itemDatesLong != null) {

            itemNamesLong = itemNamesLong.substring(1, itemNamesLong.length() - 1);
            itemPricesLong = itemPricesLong.substring(1, itemPricesLong.length() - 1);
            itemDatesLong = itemDatesLong.substring(1, itemDatesLong.length()-1);
            String[] nameArray = itemNamesLong.split(", ");
            String[] priceArray = itemPricesLong.split(", ");
            String[] datesArray = itemDatesLong.split(", ");
            itemNames = new LinkedList<>(Arrays.asList(nameArray));
            itemPrices = new LinkedList<>(Arrays.asList(priceArray));
            itemDates = new LinkedList<>(Arrays.asList(datesArray));
        }
    }

    //Purpose: Adds user input to saved variables(arrays, strings, floats, etc.)
    public void resistedBuy (View view) {
        EditText mItemName = findViewById(R.id.item_name);
        EditText mItemPrice = findViewById(R.id.item_price);
        String selectedDate;
        if (!mItemName.getText().toString().equals("") && (!mItemPrice.getText().toString().equals(""))) {

            itemNames.add(mItemName.getText().toString());
            mItemName.setText("");

            itemPrices.add(mItemPrice.getText().toString());
            totalSum = totalSum + Float.parseFloat(mItemPrice.getText().toString());
            totalSumString = "$" + totalSum;
            mDisplayTotalSum.setText(totalSumString);
            mItemPrice.setText("");

            DatePicker spinnerDate = findViewById(R.id.datePickerSpinner);
            selectedDate = (spinnerDate.getMonth() + 1) + "/" + spinnerDate.getDayOfMonth() + "/" + spinnerDate.getYear();
            itemDates.add(selectedDate);
        }

    }

    public void indulgedBuy(View view) {
        EditText mItemName = findViewById(R.id.item_name);
        EditText mItemPrice = findViewById(R.id.item_price);
        String selectedDate;
        if (!mItemName.getText().toString().equals("") && (!mItemPrice.getText().toString().equals(""))) {

            itemNames.add(mItemName.getText().toString());
            mItemName.setText("");

            itemPrices.add("-" + mItemPrice.getText().toString());
            totalSum = totalSum - Float.parseFloat(mItemPrice.getText().toString());
            totalSumString = "$" + totalSum;
            mDisplayTotalSum.setText(totalSumString);
            mItemPrice.setText("");

            DatePicker spinnerDate = findViewById(R.id.datePickerSpinner);
            selectedDate = (spinnerDate.getMonth() + 1) + "/" + spinnerDate.getDayOfMonth() + "/" + spinnerDate.getYear();
            itemDates.add(selectedDate);
        }
    }

    //Purpose: Packages saved variables into intents and sends them to FinancialHistory activity while opening said activity
    public void launchFinancialHistory(View view) {
        if (itemNames.size() > 0 && itemPrices.size() > 0) {
            Intent intent = new Intent(MainActivity.this, FinancialHistory.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, FinancialHistory.class);
            startActivity(intent);
        }
    }

    public void launchFinancialGoals(View view) {
        Intent intent = new Intent(MainActivity.this, FinancialGoals.class);
        startActivity(intent);
    }

    //Save Arrays. Works, not loading in FinancialHistory. Problem lies in FinancialHistory
    @Override
        public void onPause (){
            super.onPause();
            mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
            preferencesEditor = mPreferences.edit();
            //Saves multiple lists
            if (itemNames.size() > 0 && itemPrices.size() > 0) {
                itemNamesLong = itemNames.toString();
                itemPricesLong = itemPrices.toString();
                itemDatesLong = itemDates.toString();
                preferencesEditor.putString("namesKey", itemNamesLong);
                preferencesEditor.putString("pricesKey", itemPricesLong);
                preferencesEditor.putString("datesKey", itemDatesLong);

                preferencesEditor.putString("totalSumStringKey", totalSumString);
                preferencesEditor.putFloat("totalSumKey", totalSum);
                preferencesEditor.apply();
            } else {
                preferencesEditor.putString("totalSumStringKey", totalSumString);
                preferencesEditor.putFloat("totalSumKey", totalSum);
                preferencesEditor.apply();
            }
    }


}