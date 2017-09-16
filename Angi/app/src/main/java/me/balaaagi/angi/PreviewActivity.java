package me.balaaagi.angi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {
    ImageView previewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        previewImage= (ImageView) findViewById(R.id.capturedImagePreview);
        Bitmap bitmap = ResultHolder.getImage();

        if (bitmap == null) {
            finish();
            return;
        }

        previewImage.setImageBitmap(bitmap);
    }
}
