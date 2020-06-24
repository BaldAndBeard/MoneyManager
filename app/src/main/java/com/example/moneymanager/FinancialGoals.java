package com.example.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FinancialGoals extends AppCompatActivity {
    public String totalSumString;
    private LinearLayout linear_goals;
    public List<String> savedNameArray = new LinkedList<>();
    public List<String> savedPriceArray = new LinkedList<>();
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor preferencesEditor;
    private String sharedPrefFile = "com.example.android.MoneyManager.FinancialGoals";
    public float totalSum;
    public TextView displayTotalSum;
    GradientDrawable border = new GradientDrawable();

    String goalNameLong;
    String goalPriceLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        setContentView(R.layout.activity_financial_goals);

        totalSum = mPreferences.getFloat("totalSumKey", totalSum);
        if (mPreferences.getString("totalSumStringKey", totalSumString) != null) {
            totalSumString = "Money Saved: $" + totalSum;
        } else {
            totalSumString = "Money Saved: " + "$0";
        }

        displayTotalSum = findViewById(R.id.displayTotalSum);
        displayTotalSum.setText(totalSumString);


        //Load Shared Preferences

        //Attempt to convert arraylist to string and string to array for multiple goals

        goalNameLong = mPreferences.getString("nameKey", goalNameLong);
        goalPriceLong = mPreferences.getString("priceKey", goalPriceLong);
        if (goalNameLong != null && goalPriceLong != null) {

            goalNameLong = goalNameLong.substring(1, goalNameLong.length() - 1);
            goalPriceLong = goalPriceLong.substring(1, goalPriceLong.length() - 1);
            String[] nameArray = goalNameLong.split(", ");
            String[] priceArray = goalPriceLong.split(", ");
            savedNameArray = new LinkedList<>(Arrays.asList(nameArray));
            savedPriceArray = new LinkedList<>(Arrays.asList(priceArray));
        }


        //Use Shared Preferences to recreate user input layout
        if (savedNameArray.size() > 0) {
            for (int i = 0; i < savedNameArray.size(); i++) {
                //Purpose: Create TextViews to show variables
                TextView goal_price_textview = new TextView(this);
                TextView goal_name_textview = new TextView(this);
                goal_name_textview.setPadding(20, 10, 20, 10);
                goal_price_textview.setPadding(20, 10, 20, 10);
                final Button deleteGoal = new Button(this);
                deleteGoal.setText(R.string.delete_button);

                //New Button. Shows subtracts goal price from totalSum
                final Button boughtGoal = new Button(this);
                boughtGoal.setText(R.string.bought_button);

                //Purpose: Take in user input and set as variables
                final String goal_name = savedNameArray.get(i);
                final String goal_price = savedPriceArray.get(i);

                goal_name_textview.setText("Goal: " + goal_name);
                goal_name_textview.setTextSize(24);
                goal_name_textview.setTextColor(getResources().getColor(R.color.White));


                // Set price as a value of saved over total price
                if (totalSum < Float.parseFloat(goal_price)) {
                    goal_price_textview.setText("Price: $" + totalSum + "/" + "$" + goal_price);
                    goal_price_textview.setTextColor(getResources().getColor(R.color.White));
                } else if (totalSum >= Float.parseFloat(goal_price)) {
                    goal_price_textview.setText("Price: $" + goal_price + "/" + "$" + goal_price);
                    goal_price_textview.setTextColor(getResources().getColor(R.color.Green));
                }

                goal_price_textview.setTextSize(24);


                // Create LinearLayout(VERTICAL) to house horizontal LinearLayout
                linear_goals = findViewById(R.id.linear_goals);


                // Create RelativeLayout to house TextViews and assign said TextViews
                final RelativeLayout relativelayout_goal_child = new RelativeLayout(FinancialGoals.this);
                relativelayout_goal_child.setBackgroundColor(getResources().getColor(R.color.LightSlateGray));

                RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        300);


                RelativeLayout.LayoutParams nameTextViewParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                nameTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                RelativeLayout.LayoutParams priceTextViewParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                priceTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                priceTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                RelativeLayout.LayoutParams deleteButtonParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                deleteButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                deleteButtonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                RelativeLayout.LayoutParams boughtButtonParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                boughtButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                boughtButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                goal_name_textview.setLayoutParams(nameTextViewParams);
                goal_price_textview.setLayoutParams(priceTextViewParams);
                boughtGoal.setLayoutParams(boughtButtonParams);
                deleteGoal.setLayoutParams(deleteButtonParams);

                //Add Children to relative_goal_child
                relativelayout_goal_child.addView(goal_name_textview);
                relativelayout_goal_child.addView(goal_price_textview);
                relativelayout_goal_child.addView(boughtGoal);
                relativelayout_goal_child.addView(deleteGoal);


                // Changes background to green when goal is met. Currently changes EVERY goal to green, needs fixing.
                /*if (Float.parseFloat(goal_price) < totalSum) {
                    border.setColor(getResources().getColor(R.color.Green)); //white background
                    border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
                } else {
                    border.setColor(getResources().getColor(R.color.Gray)); //white background
                    border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
                }*/

                border.setColor(getResources().getColor(R.color.Gray)); //white background
                border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
                relativelayout_goal_child.setBackground(border);

                relativelayout_goal_child.setId(linear_goals.getChildCount());

                // Add new relativelayout_goal_child to linear_goals
                linear_goals.addView(relativelayout_goal_child, relativeLayoutParams);

                //Create an onClick handle for delete button. Works.
                deleteGoal.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(final View v) {
                                                      savedNameArray.remove(relativelayout_goal_child.getId());
                                                      savedPriceArray.remove(relativelayout_goal_child.getId());
                                                      linear_goals.removeViewAt(relativelayout_goal_child.getId());
                                                      for (int i = 0; i < linear_goals.getChildCount(); i++) {
                                                          linear_goals.getChildAt(i).setId(i);
                                                      }
                                                  }
                                              }
                );

                //Create an onClick handle for bought button. Works.
                boughtGoal.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(final View v) {
                                                      linear_goals.removeViewAt(relativelayout_goal_child.getId());
                                                      savedNameArray.remove(relativelayout_goal_child.getId());
                                                      savedPriceArray.remove(relativelayout_goal_child.getId());
                                                      totalSum = totalSum - Float.parseFloat(goal_price);
                                                      totalSumString = "Money Saved: $" + totalSum;
                                                      displayTotalSum.setText(totalSumString);
                                                      for (int i = 0; i < linear_goals.getChildCount(); i++) {
                                                          linear_goals.getChildAt(i).setId(i);
                                                      }
                                                  }
                                              }
                );

            }


        }


    }

    //Save String arraylists for user goal data
    @Override
    protected void onPause() {
        super.onPause();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        preferencesEditor = mPreferences.edit();

        //Saves multiple goals
        if (savedNameArray.size() > 0 && savedPriceArray.size() > 0) {
            goalNameLong = savedNameArray.toString();
            goalPriceLong = savedPriceArray.toString();
            preferencesEditor.putString("nameKey", goalNameLong);
            preferencesEditor.putString("priceKey", goalPriceLong);
            preferencesEditor.putString("totalSumStringKey", totalSumString);
            preferencesEditor.putFloat("totalSumKey", totalSum);
            preferencesEditor.apply();
        } else {
            preferencesEditor.clear();
            preferencesEditor.putString("totalSumStringKey", totalSumString);
            preferencesEditor.putFloat("totalSumKey", totalSum);
            preferencesEditor.apply();
        }
    }


    //Saves Multiple Goals
    public void addNewGoal(View view) {
        TextView new_goal_name = findViewById(R.id.new_goal_name);
        TextView new_goal_price = findViewById(R.id.new_goal_price);
        if (!new_goal_name.getText().toString().equals("") && !new_goal_price.getText().toString().equals("")) {
            //Purpose: Create TextViews to show variables
            TextView goal_price_textview = new TextView(this);
            TextView goal_name_textview = new TextView(this);
            goal_name_textview.setPadding(20, 10, 20, 10);
            goal_price_textview.setPadding(20, 10, 20, 10);
            final Button deleteGoal = new Button(this);
            deleteGoal.setText(R.string.delete_button);
            deleteGoal.setId(0);

            //New Button. Shows subtracts goal price from totalSum
            final Button boughtGoal = new Button(this);
            boughtGoal.setText(R.string.bought_button);




            //Purpose: Take in user input and set as variables

            String goal_name = new_goal_name.getText().toString();
            final String goal_price = new_goal_price.getText().toString();

            //Saved user input in appropriate array
            savedNameArray.add(new_goal_name.getText().toString());
            savedPriceArray.add(new_goal_price.getText().toString());


            //Purpose: Assign variables to TextViews
            goal_name_textview.setText("Goal: " + goal_name);
            goal_name_textview.setTextSize(24);
            goal_name_textview.setTextColor(getResources().getColor(R.color.White));

            // Set price as a value of saved over total price
            if (totalSum < Float.parseFloat(goal_price)) {
                goal_price_textview.setText("Price: $" + totalSum + "/" + goal_price);
                goal_price_textview.setTextColor(getResources().getColor(R.color.White));
            } else if (totalSum >= Float.parseFloat(goal_price)) {
                goal_price_textview.setText("Price: $" + goal_price + "/" + goal_price);
                goal_price_textview.setTextColor(getResources().getColor(R.color.Green));
            }

            goal_price_textview.setTextSize(24);



            // Create LinearLayout(VERTICAL) to house horizontal LinearLayout
            linear_goals = findViewById(R.id.linear_goals);


            // Create RelativeLayout to house TextViews and assign said TextViews
            final RelativeLayout linear_goal_variables = new RelativeLayout(FinancialGoals.this);
            linear_goal_variables.setBackgroundColor(getResources().getColor(R.color.LightSlateGray));

            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    300);


            RelativeLayout.LayoutParams nameTextViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            nameTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            nameTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            RelativeLayout.LayoutParams priceTextViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            priceTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            priceTextViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            RelativeLayout.LayoutParams deleteButtonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            deleteButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            deleteButtonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            RelativeLayout.LayoutParams boughtButtonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            boughtButtonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            boughtButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            goal_name_textview.setLayoutParams(nameTextViewParams);
            goal_price_textview.setLayoutParams(priceTextViewParams);
            boughtGoal.setLayoutParams(boughtButtonParams);
            deleteGoal.setLayoutParams(deleteButtonParams);


            linear_goal_variables.addView(goal_name_textview);
            linear_goal_variables.addView(goal_price_textview);
            linear_goal_variables.addView(boughtGoal);
            linear_goal_variables.addView(deleteGoal);

            // See sister comment in onCreate
            /*if (Float.parseFloat(goal_price) < totalSum) {
                border.setColor(getResources().getColor(R.color.Green)); //white background
                border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
            } else {
                border.setColor(getResources().getColor(R.color.Gray)); //white background
                border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
            }*/

            border.setColor(getResources().getColor(R.color.Gray)); //white background
            border.setStroke(5, getResources().getColor(R.color.White)); //black border with full opacity
            linear_goal_variables.setBackground(border);

            linear_goal_variables.setId(linear_goals.getChildCount());

            // Add new linear_goal_variables to linear_goals
            linear_goals.addView(linear_goal_variables, relativeLayoutParams);


            //Create an onClick handle for delete button. Works.
            deleteGoal.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(final View v) {
                                                  linear_goals.removeViewAt(linear_goal_variables.getId());
                                                  savedNameArray.remove(linear_goal_variables.getId());
                                                  savedPriceArray.remove(linear_goal_variables.getId());
                                                  for (int i = 0; i < linear_goals.getChildCount(); i++) {
                                                      linear_goals.getChildAt(i).setId(i);
                                                  }
                                              }
                                          }
            );

            //Create an onClick handle for bought button. Works.
            boughtGoal.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(final View v) {
                                                  linear_goals.removeViewAt(linear_goal_variables.getId());
                                                  savedNameArray.remove(linear_goal_variables.getId());
                                                  savedPriceArray.remove(linear_goal_variables.getId());
                                                  if (!goal_price.equals("")) {
                                                      totalSum -= Float.parseFloat(goal_price);
                                                      totalSumString = "Money Saved: $" + totalSum;
                                                      displayTotalSum.setText(totalSumString);
                                                  }
                                                  for (int i = 0; i < linear_goals.getChildCount(); i++) {
                                                      linear_goals.getChildAt(i).setId(i);
                                                  }
                                              }
                                          }
            );

        }
    }



}
