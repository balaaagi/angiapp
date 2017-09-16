package me.balaaagi.angi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import me.balaaagi.angi.network.APIClient;
import me.balaaagi.angi.network.KairosApiInterface;
import me.balaaagi.angi.network.models.DetectKairosResponse;
import me.balaaagi.angi.network.models.DetectRequestModel;
import me.balaaagi.angi.network.models.KairosDetectImages;
import me.balaaagi.angi.network.models.KairosError;
import me.balaaagi.angi.utils.AngiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviewActivity extends AppCompatActivity {
    ImageView previewImage;
    Bitmap bitmapOfFirstTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        int[] order = getIntent().getIntArrayExtra("order");
        previewImage= (ImageView) findViewById(R.id.capturedImagePreview);
        bitmapOfFirstTask = ResultHolder.getImage();
        if (bitmapOfFirstTask == null) {
            finish();
            return;
        }

        previewImage.setImageBitmap(bitmapOfFirstTask);
        boolean output=validateFirstTask();
        
    }

    private boolean validateFirstTask() {
        Log.d("Preview","Coming inside");
        KairosApiInterface kairosClient=APIClient.getRetrofitClient().create(KairosApiInterface.class);
        Call<DetectKairosResponse> detectKairosResponseCall=kairosClient.detectFacialFeatures(AngiUtils.prepareKairosHeaderMap(),new DetectRequestModel(AngiUtils.bitMapToBase64(bitmapOfFirstTask)));
        detectKairosResponseCall.enqueue(new Callback<DetectKairosResponse>() {
            @Override
            public void onResponse(Call<DetectKairosResponse> call, Response<DetectKairosResponse> response) {
                List<KairosError> errors = response.body().getErrors();
                List<KairosDetectImages> imagesResponse = response.body().getImages();
                Log.d("Preview",imagesResponse.toString());
                if(imagesResponse.get(0).getStatus().equals("Complete")){
                    Toast.makeText(getBaseContext(),"First Task Completed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"OMG First Task Not Completed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetectKairosResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Some Issue",Toast.LENGTH_SHORT).show();
            }
        });

        return false;
    }
}
