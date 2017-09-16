package me.balaaagi.angi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TripDetailsActivity extends AppCompatActivity {

    Button authenticateDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        authenticateDriver= (Button) findViewById(R.id.faceauthenticateButton);
        authenticateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraAc=new Intent(getApplicationContext(),CaptureActivity.class);
                startActivity(cameraAc);
            }
        });
    }
}
