package me.balaaagi.angi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactSupportActvity extends AppCompatActivity {

    Button contactSupport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support_actvity);
        contactSupport= (Button) findViewById(R.id.contactSupportButton);
        contactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:04449361234"));
                startActivity(callIntent);
            }
        });

    }
}
