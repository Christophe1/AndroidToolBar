package com.example.chris.androiftoolbar;

/**
 * Created by Chris on 12/12/2017.
 */

public class SelectPhoneContact {

    String phone;

    public String getPhone() {return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //*****************************************
    //this is for the checkbox
    boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected){

        this.selected = selected;

    }


}
