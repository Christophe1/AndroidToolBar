package com.example.chris.androiftoolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;

    String [] listSource = {

            "Harry",
            "Hermione",
            "Voldemort",
            "Ron",
            "Dumbledore",
            "Draco",
            "Crabbe",
            "Goyle",
            "Lucius Malfoy",
            "Snape",
            "Sprout",
            "Flitwick",
            "McGonagall"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listSource);
        listView.setAdapter(adapter);


        //MenuItem item = menu.findItem(R.id.search_view);
        searchView = (SearchView)findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    List<String> listFound = new ArrayList<String>();
                    for (String item : listSource) {
                        if (item.contains(newText))
                            listFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listFound);
                    listView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listSource);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });
    }

    //creates the menu
    //the 3 dots are made with app:showAsAction="never"
    //in menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_contact:
                //start the NewContact class

                Intent intent = new Intent(MainActivity.this, NewContact.class);

                startActivity(intent);

                System.out.println("New Contact has been clicked");


                //execute the AsyncTask
               // MainActivity.StartUp startUp = new MainActivity.StartUp();
                //startUp.execute();

/*                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    startUp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    startUp.execute();*/





//                Intent intent = new Intent(MainActivity.this, NewContact.class);
//
//                startActivity(intent);
                return true;
        }
        return false;
    }



    private class StartUp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Intent intent = new Intent(MainActivity.this, NewContact.class);

            startActivity(intent);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
