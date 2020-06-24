package com.example.moneymanager;


import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    public TextView displayTotalSum;
    public LinearLayout linear_goals;
    GradientDrawable border = new GradientDrawable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_financial_history);

        ////Load Shared Preferences
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


            totalSum = mPreferences.getFloat("totalSumKey", totalSum);
            if (mPreferences.getString("totalSumStringKey", totalSumString) != null) {
                totalSumString = Float.toString(totalSum);
            } else {
                totalSumString = "$0";
            }

            displayTotalSum = findViewById(R.id.displayTotalSum);
            displayTotalSum.setText(totalSumString);



            //Use Shared Preferences to recreate user input layout
            if (itemNames.size() > 0) {
                for (int i = 0; i < itemNames.size(); i++) {
                    //Purpose: Create TextViews to show variables
                    TextView goal_price_textview = new TextView(this);
                    TextView goal_name_textview = new TextView(this);
                    TextView goal_date_textview = new TextView(this);
                    goal_name_textview.setPadding(20, 10, 20, 10);
                    goal_price_textview.setPadding(20, 10, 20, 10);
                    final Button deleteGoal = new Button(this);
                    deleteGoal.setText(R.string.delete_button);
                    deleteGoal.setTextSize(12);
                    deleteGoal.setHeight(8);

                    // Set deleteGoal button to an icon
                    /*deleteGoal.setImageDrawable(getDrawable(R.drawable.ic_action_name));
                    deleteGoal.setScaleX((float) 0.5);
                    deleteGoal.setScaleY((float) 0.5);*/


                    //Purpose: Take in user input and set as variables
                    final String item_name = itemNames.get(i);
                    final String item_price = itemPrices.get(i);
                    final String item_date = itemDates.get(i);

                    goal_name_textview.setText("Name: " + item_name);
                    goal_name_textview.setTextSize(12);
                    goal_name_textview.setTextColor(getResources().getColor(R.color.White));
                    goal_price_textview.setText("Price: $" + item_price);
                    goal_price_textview.setTextSize(12);
                    goal_price_textview.setTextColor(getResources().getColor(R.color.White));
                    goal_date_textview.setText("Date: " + item_date);
                    goal_date_textview.setTextSize(12);
                    goal_date_textview.setTextColor(getResources().getColor(R.color.White));


                    // Create LinearLayout(VERTICAL) to house horizontal LinearLayout
                    linear_goals = findViewById(R.id.linear_goals);


                    // Create RelativeLayout to house TextViews and assign said TextViews
                    final LinearLayout relativelayout_goal_child = new LinearLayout(FinancialHistory.this);
                    relativelayout_goal_child.setBackgroundColor(getResources().getColor(R.color.LightSlateGray));

                    //Add Children to relative_goal_child
                    relativelayout_goal_child.addView(goal_name_textview);
                    relativelayout_goal_child.addView(goal_price_textview);
                    relativelayout_goal_child.addView(goal_date_textview);
                    relativelayout_goal_child.addView(deleteGoal);

                    border.setColor(getResources().getColor(R.color.Gray)); //white background
                    border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
                    relativelayout_goal_child.setBackground(border);



                    // Delete old history to never have more than 20 at a time.
                    /*if (linear_goals.getChildCount() >= 20) {
                        linear_goals.removeViewAt(0);
                        for (i = 0; i < linear_goals.getChildCount(); i++) {
                            linear_goals.getChildAt(i).setId(i);
                        }
                    }*/

                    // Add new relativelayout_goal_child to linear_goals
                    relativelayout_goal_child.setId(linear_goals.getChildCount());
                    linear_goals.addView(relativelayout_goal_child);

                    //Create an onClick handle for delete button. Works.
                    deleteGoal.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(final View v) {
                                                          itemNames.remove(relativelayout_goal_child.getId());
                                                          itemPrices.remove(relativelayout_goal_child.getId());
                                                          itemDates.remove(relativelayout_goal_child.getId());
                                                          linear_goals.removeViewAt(relativelayout_goal_child.getId());
                                                          for (int i = 0; i < linear_goals.getChildCount(); i++) {
                                                              linear_goals.getChildAt(i).setId(i);
                                                          }
                                                      }
                                                  }
                    );

                }


            }


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