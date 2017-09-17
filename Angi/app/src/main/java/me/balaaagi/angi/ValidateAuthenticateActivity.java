package me.balaaagi.angi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import me.balaaagi.angi.network.APIClient;
import me.balaaagi.angi.network.KairosApiInterface;
import me.balaaagi.angi.network.models.KairosError;
import me.balaaagi.angi.network.models.KairosRecognizeImages;
import me.balaaagi.angi.network.models.RecognizeKairosResponse;
import me.balaaagi.angi.network.models.RecognizeRequestModel;
import me.balaaagi.angi.utils.AngiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateAuthenticateActivity extends AppCompatActivity {
    Bitmap bitmapOfFirstTask;
    ProgressDialog taskValidationProgress;
    ImageView authenticeView;
    boolean validated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_authenticate);
        authenticeView= (ImageView) findViewById(R.id.authenticateCapturedImage);
        bitmapOfFirstTask = ResultHolder.getImage();
        taskValidationProgress = new ProgressDialog(this);
        if (bitmapOfFirstTask == null) {
            finish();
            return;
        }

        authenticeView.setImageBitmap(bitmapOfFirstTask);
        boolean output=authenticateFace();
    }

    private boolean authenticateFace() {
        KairosApiInterface kairosClient= APIClient.getRetrofitClient().create(KairosApiInterface.class);
        Call<RecognizeKairosResponse> recognizeKairosResponseCall=kairosClient.recognizeFace("application/json",BuildConfig.KAIROS_APP_ID,BuildConfig.KAIROS_API_KEY,new RecognizeRequestModel(AngiUtils.bitMapToBase64(bitmapOfFirstTask),BuildConfig.KAIROS_GALLERY));
        Log.d("Photo",recognizeKairosResponseCall.request().url().toString());
        Log.d("Photo",recognizeKairosResponseCall.request().headers().toString());
        taskValidationProgress.setIndeterminate(false);
        taskValidationProgress.setMessage("Validating Your Face ...");
        taskValidationProgress.setTitle("Angi App");
        taskValidationProgress.show();
        recognizeKairosResponseCall.enqueue(new Callback<RecognizeKairosResponse>() {
            @Override
            public void onResponse(Call<RecognizeKairosResponse> call, Response<RecognizeKairosResponse> response) {
                List<KairosError> errors = response.body().getErrors();
                List<KairosRecognizeImages> imagesResponse = response.body().getImages();
                if(imagesResponse!=null){
                    Log.d("Photo",imagesResponse.get(0).getTransaction().getStatus());

                    if(imagesResponse.get(0).getTransaction().getStatus().equals("success")){
                        taskValidationProgress.dismiss();
                        Intent photoAuthenticate=new Intent(ValidateAuthenticateActivity.this,SuccessActivity.class);
                        startActivity(photoAuthenticate);
                    }else{
                        taskValidationProgress.dismiss();
                        validated=false;
                        Toast.makeText(getBaseContext(),"Authentication Validation Failed",Toast.LENGTH_SHORT).show();
                        Intent contactSupportIntent=new Intent(ValidateAuthenticateActivity.this,ContactSupportActvity.class);
                        startActivity(contactSupportIntent);
                    }

                }else{
                    Log.d("Photo",errors.get(0).getMessage());
                    taskValidationProgress.dismiss();
                    validated=false;
                    Toast.makeText(getBaseContext(),"Authentication Validation Failed",Toast.LENGTH_SHORT).show();
                    Intent contactSupportIntent=new Intent(ValidateAuthenticateActivity.this,ContactSupportActvity.class);
                    startActivity(contactSupportIntent);
                }
            }

            @Override
            public void onFailure(Call<RecognizeKairosResponse> call, Throwable t) {
                Log.d("Photo","Inside Failure");
                taskValidationProgress.dismiss();
                validated=false;
                Toast.makeText(getBaseContext(),"Liveliness Validation Failed",Toast.LENGTH_SHORT).show();
                Intent contactSupportIntent=new Intent(ValidateAuthenticateActivity.this,ContactSupportActvity.class);
                startActivity(contactSupportIntent);

            }
        });
        return false;
    }
}
