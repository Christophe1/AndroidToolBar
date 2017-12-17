package com.example.chris.androiftoolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chris on 13/12/2017.
 */

public class VerifyUserPhoneNumber extends AppCompatActivity {

    //allPhonesofContacts is a list of all the phone numbers in the user's contacts
    public static final ArrayList<String> allPhonesofContacts = new ArrayList<String>();

    //allNamesofContacts is a list of all the names in the user's contacts
    public static final ArrayList<String> allNamesofContacts = new ArrayList<String>();

    Cursor cursor;
    String name;
    String phoneNumberofContact;

    TextView txtCountryCode;
    String CountryCode;
    String phoneNoofUserbeforeE164;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.verify_phone_number);

        //get all the contacts on the user's phone
        //getPhoneContacts();
    }


    private class StartUpInfo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            //get all the contacts on the user's phone
            //getPhoneContacts();


            //get the names and phone numbers of all phone contacts in phone book, take out duplicates
            //and put the phone numbers in E164 format
           // private void getPhoneContacts() {

//          we have this here to avoid cursor errors
                if (cursor != null) {
                    cursor.moveToFirst();

                }


                try {

//                get a handle on the Content Resolver, so we can query the provider,
                    cursor = getApplicationContext().getContentResolver()
//                the table to query
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//               Null. This means that we are not making any conditional query into the contacts table.
//               Hence, all data is returned into the cursor.
//               Projection - the columns you want to query
                                    null,
//               Selection - with this you are extracting records with assigned (by you) conditions and rules
                                    null,
//               SelectionArgs - This replaces any question marks (?) in the selection string
//               if you have something like String[] args = { "first string", "second@string.com" };
                                    null,
//               display in ascending order
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

//                get the column number of the Contact_ID column, make it an integer.
//                I think having it stored as a number makes for faster operations later on.
//            int Idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
//                get the column number of the DISPLAY_NAME column
                    int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//                 get the column number of the NUMBER column
                    int phoneNumberofContactIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    cursor.moveToFirst();

//              We make a new Hashset to hold all our contact_ids, including duplicates, if they come up
                    Set<String> ids = new HashSet<>();
//              We make a new Hashset to hold all our lookup keys, including duplicates, if they come up
//            Set<String> ids2 = new HashSet<>();
                    do {
                         System.out.println("=====>in while");



//                        get a handle on the display name, which is a string
                        name = cursor.getString(nameIdx);

//                        get a handle on the phone number, which is a string
                        phoneNumberofContact = cursor.getString(phoneNumberofContactIdx);

                        //----------PUT INTO E164 FORMAT--------------------------------------
                        //need to strip all characters except numbers and + (+ for the first character)
                        phoneNumberofContact = phoneNumberofContact.replaceAll("[^+0-9]", "");
                        //replace numbers starting with 00 with +
                        if (phoneNumberofContact.startsWith("00")) {
                            phoneNumberofContact = phoneNumberofContact.replaceFirst("00", "+");
                        }

                        //all phone numbers not starting with +, make them E.164 format,
                        //for the country code the user has chosen.
                        if (!phoneNumberofContact.startsWith("+")) {
                            //CountryCode is the country code chosen by the user originally
                            phoneNumberofContact = String.valueOf(CountryCode) + String.valueOf(phoneNumberofContact);

       /*             PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                    try {
                        //if phone number on user's phone is not in E.164 format,
                        //precede the number with user's country code.
                        Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumberofContact, "");
                        phoneNumberofContact = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
                        //If an error happens :
                    } catch (NumberParseException e) {
                        System.err.println("NumberParseException was thrown: " + e.toString());
                        // System.out.println(phoneNumberofContact);
                    }*/
                        }

                        //----------------------------------------------------------

//                  if our Hashset doesn't already contain the phone number string,
//                    then add it to the hashset
                        if (!ids.contains(phoneNumberofContact)) {
                            ids.add(phoneNumberofContact);

                            //allPhonesofContacts is a list of all the phone numbers in the user's contacts
                            allPhonesofContacts.add(phoneNumberofContact);

                            //allNamesofContacts is a list of all the names in the user's contacts
                            allNamesofContacts.add(name);

                    System.out.println(" Name--->" + name);
                    System.out.println(" Phone number of contact--->" + phoneNumberofContact);



                            //we will save the array list allPhonesofContacts,
                            //with this we will put all phone names of contacts on user's phone into our ListView, in other activities
                            SharedPreferences sharedPreferencesallPhonesofContacts = PreferenceManager.getDefaultSharedPreferences(getApplication());
                            SharedPreferences.Editor prefsEditor = sharedPreferencesallPhonesofContacts.edit();

                            Gson gson = new Gson();
                            String json = gson.toJson(allPhonesofContacts);
                            prefsEditor.putString("allPhonesofContacts", json);
                            prefsEditor.commit();


                            //now, let's put in the string of phone numbers
                            //save the array list allNamesofContacts,
                            //with this we will put all phone names of contacts on user's phone into our ListView, in other activities
                            SharedPreferences sharedPreferencesallNamesofContacts = PreferenceManager.getDefaultSharedPreferences(getApplication());
                            SharedPreferences.Editor prefsEditor2 = sharedPreferencesallNamesofContacts.edit();

                            //now, let's put in the string of names
                            Gson gsonNames = new Gson();
                            String jsonNames = gsonNames.toJson(allNamesofContacts);
                            prefsEditor2.putString("allNamesofContacts", jsonNames);
                            prefsEditor2.commit();
                        }

                    }




                    while (cursor.moveToNext());
            System.out.println("size of allPhonesofContacts :" + allPhonesofContacts.size());
            System.out.println("here is the list of allPhonesofContacts :" + allPhonesofContacts);
            System.out.println("size of all names :" + allNamesofContacts.size());
            System.out.println("here is the list of names in contacts :" + allNamesofContacts);



                }



                catch (Exception e) {
                    e.printStackTrace();
                    cursor.close();
                } finally {
//                if (cursor != null) {
                    cursor.close();
//                }
                }




            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            // then start the next activity, PopulistoListView
            Intent myIntent1 = new Intent(VerifyUserPhoneNumber.this, MainActivity.class);
            // myIntent1.putExtra("phoneNumberofContact", phoneNumberofContact);
            // myIntent1.putExtra("phoneNameofContact", name);
            VerifyUserPhoneNumber.this.startActivity(myIntent1);



        }
    }




}







