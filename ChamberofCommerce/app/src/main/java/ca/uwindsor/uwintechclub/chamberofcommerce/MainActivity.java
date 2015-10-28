package ca.uwindsor.uwintechclub.chamberofcommerce;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String apiURL = "https://uwindsor.herokuapp.com/?q=";
    MainActivity that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setVisibility(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        that = this;





        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                queryAPI();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryAPI();
                return true;
            }

            public void callSearch(String query) {
                //Do searching
            }

        });

//        searchView.setQuery("a",true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }


    public void queryAPI() {

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
//        EditText emailEditText = (EditText) findViewById(R.id.email_address);
        String query = searchView.getQuery().toString();

        if( query != null && !query.isEmpty()) {

            String urlString = apiURL + query;

            System.out.println("hanji");
            System.out.println(urlString);
            new CallAPI().execute(urlString);

        }
    }





        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    ArrayList finalResult = new ArrayList<String>();
    ArrayList finalResultRecord = new ArrayList<record>();

    class record{

        String ucDirectory_UcListing_lblOwner;
        String ucDirectory_UcListing_lblTitle;
        String ucDirectory_UcListing_lblAddress1;
        String ucDirectory_UcListing_lblCity;
        String ucDirectory_UcListing_lblStateProvince;
        String ucDirectory_UcListing_lblZipPostal;
        String ucDirectory_UcListing_lblFax;
        String ucDirectory_UcListing_lblPhone1;
        String ucDirectory_UcListing_hlEmail;
        String ucDirectory_UcListing_hlWebsit;

    }

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0]; // URL to call

            System.out.println("I need to call");
            System.out.println(urlString);
            String resultToDisplay = "";

            InputStream in = null;

            StringBuilder result;
            // HTTP Get
            try {

                URL url = new URL(urlString);

                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }


//                System.out.println(in.toString());

            } catch (Exception e) {

                System.out.println(e.getMessage());

                return e.getMessage();

            }

            try {
                JSONArray jsonarray = new JSONArray(result.toString());

//                final ArrayList<String> list = new ArrayList<String>();

//                String[] values = new String[500];

                finalResult = new ArrayList<String>();

                finalResultRecord = new ArrayList<record>();



                for (int i = 0; i < jsonarray.length(); i++) {
                    record currentRecord = new record();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String name = jsonobject.getString("ucdirectory_uclisting_lblowner");

                    if(jsonobject.has("ucdirectory_uclisting_lblowner")){
                        currentRecord.ucDirectory_UcListing_lblOwner = jsonobject.getString("ucdirectory_uclisting_lblowner");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lbltitle")){
                        currentRecord.ucDirectory_UcListing_lblTitle = jsonobject.getString("ucdirectory_uclisting_lbltitle");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lbladdress1")){
                        currentRecord.ucDirectory_UcListing_lblAddress1 = jsonobject.getString("ucdirectory_uclisting_lbladdress1");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lblcity")){
                        currentRecord.ucDirectory_UcListing_lblCity = jsonobject.getString("ucdirectory_uclisting_lblcity");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lblstateprovince")){
                        currentRecord.ucDirectory_UcListing_lblStateProvince = jsonobject.getString("ucdirectory_uclisting_lblstateprovince");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lblzippostal")){
                        currentRecord.ucDirectory_UcListing_lblZipPostal = jsonobject.getString("ucdirectory_uclisting_lblzippostal");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lblfax")){
                        currentRecord.ucDirectory_UcListing_lblFax = jsonobject.getString("ucdirectory_uclisting_lblfax");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_lblphone1")){
                        currentRecord.ucDirectory_UcListing_lblPhone1 = jsonobject.getString("ucdirectory_uclisting_lblphone1");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_hlemail")){
                        currentRecord.ucDirectory_UcListing_hlEmail = jsonobject.getString("ucdirectory_uclisting_hlemail");
                    }
                    if(jsonobject.has("ucdirectory_uclisting_hlwebsite")){
                        currentRecord.ucDirectory_UcListing_hlWebsit = jsonobject.getString("ucdirectory_uclisting_hlwebsite");
                    }

                    finalResultRecord.add(currentRecord);

                    finalResult.add(name);
//                    values[i] = name;
                }













            }catch(Exception e){

                System.out.println("some error");

                System.out.println(e.getMessage());
            }
            return resultToDisplay;

        }

        protected void onPostExecute(String result) {





            final ListView listView = (ListView) findViewById(R.id.listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(that,
                    android.R.layout.simple_list_item_1, finalResult);





            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();



                    record currentRecord = (record) finalResultRecord.get(itemPosition);

//                    Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    Bundle b = new Bundle();
//                    b.putInt("key", 1); //Your id
                    b.putString("ucDirectory_UcListing_lblOwner", currentRecord.ucDirectory_UcListing_lblOwner);

                    System.out.println(currentRecord.ucDirectory_UcListing_lblOwner);

                    System.out.println(currentRecord.ucDirectory_UcListing_lblStateProvince);

                    System.out.println("peace");

                    b.putString("ucDirectory_UcListing_lblTitle", currentRecord.ucDirectory_UcListing_lblTitle);
                    b.putString("ucDirectory_UcListing_lblAddress1", currentRecord.ucDirectory_UcListing_lblAddress1);
                    b.putString("ucDirectory_UcListing_lblCity", currentRecord.ucDirectory_UcListing_lblCity);
                    b.putString("ucDirectory_UcListing_lblStateProvince", currentRecord.ucDirectory_UcListing_lblStateProvince);
                    b.putString("ucDirectory_UcListing_lblZipPostal", currentRecord.ucDirectory_UcListing_lblZipPostal);
                    b.putString("ucDirectory_UcListing_lblFax", currentRecord.ucDirectory_UcListing_lblFax);
                    b.putString("ucDirectory_UcListing_lblPhone1", currentRecord.ucDirectory_UcListing_lblPhone1);
                    b.putString("ucDirectory_UcListing_hlEmail", currentRecord.ucDirectory_UcListing_hlEmail);
                    b.putString("ucDirectory_UcListing_hlWebsit", currentRecord.ucDirectory_UcListing_hlWebsit);
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);




                }

            });



            System.out.println(result);
        }


    }
    }
