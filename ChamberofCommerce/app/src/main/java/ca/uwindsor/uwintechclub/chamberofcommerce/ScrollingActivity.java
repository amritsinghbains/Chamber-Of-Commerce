package ca.uwindsor.uwintechclub.chamberofcommerce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();

        String ucDirectory_UcListing_lblOwner = b.getString("ucDirectory_UcListing_lblOwner");
        String ucDirectory_UcListing_lblTitle = b.getString("ucDirectory_UcListing_lblTitle");
        String ucDirectory_UcListing_lblAddress1 = b.getString("ucDirectory_UcListing_lblAddress1");
        String ucDirectory_UcListing_lblCity = b.getString("ucDirectory_UcListing_lblCity");
        String ucDirectory_UcListing_lblStateProvince = b.getString("ucDirectory_UcListing_lblStateProvince");
        String ucDirectory_UcListing_lblZipPostal = b.getString("ucDirectory_UcListing_lblZipPostal");
        String ucDirectory_UcListing_lblFax = b.getString("ucDirectory_UcListing_lblFax");
        String ucDirectory_UcListing_lblPhone1 = b.getString("ucDirectory_UcListing_lblPhone1");
        String ucDirectory_UcListing_hlEmail = b.getString("ucDirectory_UcListing_hlEmail");
        String ucDirectory_UcListing_hlWebsit = b.getString("ucDirectory_UcListing_hlWebsit");


//        addHelpText(R.id.ucDirectory_UcListing_lblOwner, ucDirectory_UcListing_lblOwner);

        setTitle(ucDirectory_UcListing_lblOwner);

        addHelpText(R.id.ucDirectory_UcListing_lblTitle, ucDirectory_UcListing_lblTitle);
        addHelpText(R.id.ucDirectory_UcListing_lblAddress1, ucDirectory_UcListing_lblAddress1);
        addHelpText(R.id.ucDirectory_UcListing_lblCity, ucDirectory_UcListing_lblCity);
        addHelpText(R.id.ucDirectory_UcListing_lblStateProvince, ucDirectory_UcListing_lblStateProvince);
        addHelpText(R.id.ucDirectory_UcListing_lblZipPostal, ucDirectory_UcListing_lblZipPostal);
        addHelpText(R.id.ucDirectory_UcListing_lblFax, ucDirectory_UcListing_lblFax);
        addHelpText(R.id.ucDirectory_UcListing_lblPhone1, ucDirectory_UcListing_lblPhone1);
        addHelpText(R.id.ucDirectory_UcListing_hlEmail, ucDirectory_UcListing_hlEmail);
        addHelpText(R.id.ucDirectory_UcListing_hlWebsit, ucDirectory_UcListing_hlWebsit);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                TextView textViewToChange = (TextView) findViewById(R.id.ucDirectory_UcListing_lblPhone1);
                callIntent.setData(Uri.parse("tel:" + textViewToChange.getText()));
                startActivity(callIntent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    public void addHelpText(int finder, String text) {

//        System.out.println(text);

        TextView textViewToChange = (TextView) findViewById(finder);
        textViewToChange.setText(text);
    }
}
