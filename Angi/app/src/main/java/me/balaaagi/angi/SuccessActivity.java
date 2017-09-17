package me.balaaagi.angi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {
    TextView driverIdentifiedName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        String nameIdentified=getIntent().getStringExtra("name");
        driverIdentifiedName= (TextView) findViewById(R.id.authenitcatedDriverName);
        driverIdentifiedName.setText("Hi,"+nameIdentified);
        driverIdentifiedName.setTextSize(15);
    }
}
