package me.balaaagi.angi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button tripDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tripDetails= (Button) findViewById(R.id.tripDetailsButton);
        tripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tripDetailsIntent=new Intent(getApplicationContext(),TripDetailsActivity.class);
                startActivity(tripDetailsIntent);
            }
        });
    }
}
