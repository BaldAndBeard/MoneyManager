package com.example.moneymanager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.LinkedList;

public class FinancialHistory extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_financial_history);

        //Considers itemDatesLong to be null. It sees the other two.
        itemNamesLong = mPreferences.getString("namesKey", itemNamesLong);
        itemPricesLong = mPreferences.getString("pricesKey", itemPricesLong);
        itemDatesLong = mPreferences.getString("datesKey", itemDatesLong);


        totalSum = mPreferences.getFloat("totalSumKey", totalSum);
        totalSumString = mPreferences.getString("totalSumStringKey", totalSumString);


        if (itemNamesLong != null && itemPricesLong != null && itemDatesLong != null) {

            itemNamesLong = itemNamesLong.substring(1, itemNamesLong.length() - 1);
            itemPricesLong = itemPricesLong.substring(1, itemPricesLong.length() - 1);
            itemDatesLong = itemDatesLong.substring(1, itemDatesLong.length() - 1);
            String[] nameArray = itemNamesLong.split(", ");
            String[] priceArray = itemPricesLong.split(", ");
            String[] datesArray = itemDatesLong.split(", ");
            itemNames = new LinkedList<>(Arrays.asList(nameArray));
            itemPrices = new LinkedList<>(Arrays.asList(priceArray));
            itemDates = new LinkedList<>(Arrays.asList(datesArray));

            TextView dateTextView = findViewById(R.id.item_date_list);
            String displayItemDates = (itemDates.get(0));
            dateTextView.setText(displayItemDates);

            for (int i = 1; i < itemDates.size(); i++) {
                displayItemDates += "\n" + (itemDates.get(i));
                dateTextView.setText(displayItemDates);
            }

            TextView nameTextView = findViewById(R.id.item_name_list);
            String displayItemNames = (itemNames.get(0));
            nameTextView.setText(displayItemNames);

            for (int i = 1; i < itemNames.size(); i++) {
                displayItemNames += "\n" + (itemNames.get(i));
                nameTextView.setText(displayItemNames);
            }

            TextView priceTextView = findViewById(R.id.item_price_list);
            String displayItemPrices = "$" + (itemPrices.get(0));
            priceTextView.setText(displayItemPrices);

            for (int i = 1; i < itemPrices.size(); i++) {
                displayItemPrices += "\n" + "$" + (itemPrices.get(i));
                priceTextView.setText(displayItemPrices);
            }

            TextView displayTotalSum = findViewById(R.id.total_sum);
            displayTotalSum.setText(Float.toString(totalSum));

            String totalItems = Integer.toString(itemNames.size());
            TextView displayTotalItems = findViewById(R.id.total_count);
            displayTotalItems.setText(totalItems);
        }

    }

    //Save Arrays. Not Working
    @Override
    public void onPause() {
        super.onPause();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();
        //Saves multiple lists
        if (itemNames.size() > 0 && itemPrices.size() > 0) {
            itemNamesLong = itemNames.toString();
            itemPricesLong = itemPrices.toString();
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