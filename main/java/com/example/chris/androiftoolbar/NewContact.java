package com.example.chris.androiftoolbar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 10/12/2017.
 */

public class NewContact extends AppCompatActivity {

    // ArrayList called selectPhoneContacts that will contain SelectPhoneContact info
    ArrayList<SelectPhoneContact> selectPhoneContacts;

    ArrayList <String> allPhonesofContacts;
    ArrayList <String> allNamesofContacts;

    ArrayList<String> matchingContacts = new ArrayList<String>();

    //define a list made out of SelectPhoneContacts and call it theContactsList
    public List<SelectPhoneContact> theContactsList;
    //define an array list made out of SelectContacts and call it arraylist
    private ArrayList<SelectPhoneContact> arraylist;

    String phoneNumberofContact;
    String phoneNameofContact;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadContact loadContact = new LoadContact();
        loadContact.execute();

/*        theContactsList = selectPhoneContacts;
        this.arraylist = new ArrayList<SelectPhoneContact>();
        this.arraylist.addAll(theContactsList);*/
        selectPhoneContacts = new ArrayList<SelectPhoneContact>();
       // System.out.println("loaded 1");
        recyclerView = (RecyclerView) findViewById(R.id.rv);
    }

/*    public void makethelist(final int i) {

        //selectPhoneContacts is an empty array list that will hold our SelectPhoneContact info
       // selectPhoneContacts = new ArrayList<SelectPhoneContact>();
        final SelectPhoneContact data = (SelectPhoneContact) arraylist.get(i);
        //  SelectPhoneContact data = theContactsList;
        //Cursor data = this.arraylist;
        List<SelectPhoneContact> contacts = new ArrayList<>();
        int i = 0;
        if (data.getCount)
    }*/

    //******for the phone contacts in the listview

    // Load data in background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            System.out.println("loaded 2");
            //we want to delete the old selectContacts from the listview when the Activity loads
            //because it may need to be updated and we want the user to see the updated listview,
            //like if the user adds new names and numbers to their phone contacts.
//            selectPhoneContacts.clear();

            //we are fetching the array list allPhonesofContacts, created in VerifyUserPhoneNumber.
            //with this we will put all phone numbers of contacts on user's phone into our ListView in NewContact activity
            SharedPreferences sharedPreferencesallPhonesofContacts = PreferenceManager.getDefaultSharedPreferences(getApplication());
            Gson gson = new Gson();
            String json = sharedPreferencesallPhonesofContacts.getString("allPhonesofContacts", "");
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            allPhonesofContacts = gson.fromJson(json, type);
            System.out.println("NewContact: allPhonesofContacts :" + allPhonesofContacts);

            //we are fetching the array list allNamesofContacts, created in VerifyUserPhoneNumber.
            //with this we will put all phone names of contacts on user's phone into our ListView in NewContact activity
            SharedPreferences sharedPreferencesallNamesofContacts = PreferenceManager.getDefaultSharedPreferences(getApplication());
            Gson gsonNames = new Gson();
            String jsonNames = sharedPreferencesallNamesofContacts.getString("allNamesofContacts", "");
            Type typeNames = new TypeToken<ArrayList<String>>() {
            }.getType();
            allNamesofContacts = gson.fromJson(jsonNames, type);
            System.out.println("NewContact: allNamesofContacts :" + allNamesofContacts);

            System.out.println("NewContact:the amount in allPhonesofContacts :" + allPhonesofContacts.size());
            System.out.println("NewContact:the amount in allNamesofContacts :" + allNamesofContacts.size());

            matchingContacts.add("+3531234567");
            matchingContacts.add("+353868132813");
            matchingContacts.add("+353863366715");
            matchingContacts.add("+353858716422");

            //for every value in the allPhonesofContacts array list, call it phoneNumberofContact
            for (int i = 0; i < allPhonesofContacts.size(); i++) {

                phoneNumberofContact = allPhonesofContacts.get(i);
                phoneNameofContact = allNamesofContacts.get(i);

                System.out.println("SelectPhoneContactAdapter: phoneNumberofContact : " + phoneNumberofContact);
                System.out.println("SelectPhoneContactAdapter: phoneNameofContact : " + phoneNameofContact);

                SelectPhoneContact selectContact = new SelectPhoneContact();

                selectPhoneContacts.add(selectContact);

                selectContact.setName(phoneNameofContact);
                //    selectContact.setPhone(phoneNumberofContact);
                selectContact.setPhone(phoneNumberofContact);
                //selectContact.setSelected(is);


            }




            return null;


        }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(selectPhoneContacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(NewContact.this)));
        //should really have an else, in case the list is 0 and to avoid errors

/*        adapter.radioButtontoPhoneContacts = new changeRadioButtontoPhoneContacts() {
            //@Override
            public void update() {
                NewContact.this.rbu1.setChecked(true);
            }*/
        };

        //we need to notify the listview that changes may have been made on
        //the background thread, doInBackground, like adding or deleting contacts,
        //and these changes need to be reflected visibly in the listview. It works
        //in conjunction with selectContacts.clear()
        //adapter.notifyDataSetChanged();


        //********************

        //this function measures the height of the listview, with all the contacts, and loads it to be that
        //size. We need to do this because there's a problem with a listview in a scrollview.
        //The function is in GlobalFunctions
        //GlobalFunctions.justifyListViewHeightBasedOnChildren(NewContact.this,listView);
        //call the hidePDialog function, hide the loading dialog
       // hidePDialog();

    }


    @Override
    protected void onResume() {

        super.onResume();

        LoadContact loadContact = new LoadContact();
        loadContact.execute();
    }


}

