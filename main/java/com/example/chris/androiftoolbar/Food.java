package com.example.chris.androiftoolbar;

/**
 * Created by Chris on 12/12/2017.
 */

public class Food {
    private String Meal;
    private String Desert;

    public Food(String mMeal, String mDesert){
        Meal = mMeal;
        Desert = mDesert;
    }
    public String getMeal() {return Meal;}
    public String getDesert() {return Desert;}
}
