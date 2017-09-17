package me.balaaagi.angi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

import java.util.List;

import me.balaaagi.angi.network.APIClient;
import me.balaaagi.angi.network.KairosApiInterface;
import me.balaaagi.angi.network.models.DetectKairosResponse;
import me.balaaagi.angi.network.models.DetectRequestModel;
import me.balaaagi.angi.network.models.KairosDetectImages;
import me.balaaagi.angi.network.models.KairosError;
import me.balaaagi.angi.network.models.KairosRecognizeImages;
import me.balaaagi.angi.network.models.RecognizeKairosResponse;
import me.balaaagi.angi.network.models.RecognizeRequestModel;
import me.balaaagi.angi.utils.AngiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoCapture extends AppCompatActivity implements View.OnLayoutChangeListener{
    CameraView photoCapture;
    Button authenticatButton;
    ProgressDialog taskValidationProgress;
    Bitmap capturedFace;
    boolean validated=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        photoCapture= (CameraView) findViewById(R.id.cameraAuthenticate);
        authenticatButton= (Button) findViewById(R.id.clicktoAunthenticate);
        taskValidationProgress = new ProgressDialog(this);
        photoCapture.addOnLayoutChangeListener(this);
        authenticatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureToAuthenticate();
            }
        });
    }

    private void captureToAuthenticate() {

        final long startTime = System.currentTimeMillis();
        photoCapture.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                long callbackTime = System.currentTimeMillis();
                Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                ResultHolder.dispose();
                ResultHolder.setImage(bitmap);
                ResultHolder.setNativeCaptureSize(photoCapture.getPreviewSize());
                ResultHolder.setTimeToCallback(callbackTime - startTime);
                capturedFace=bitmap;
                boolean authenticated=authenticateFace(capturedFace);
            }
        });
        photoCapture.captureImage();

    }

    private boolean authenticateFace(Bitmap capturedFace) {
        KairosApiInterface kairosClient= APIClient.getRetrofitClient().create(KairosApiInterface.class);
        Call<RecognizeKairosResponse> recognizeKairosResponseCall=kairosClient.recognizeFace("application/json",BuildConfig.KAIROS_APP_ID,BuildConfig.KAIROS_API_KEY,new RecognizeRequestModel(AngiUtils.bitMapToBase64(capturedFace),BuildConfig.KAIROS_GALLERY));
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
                    if(imagesResponse.get(0).getTransaction().getStatus().equals("success")){
                        taskValidationProgress.dismiss();
                        Intent photoAuthenticate=new Intent(PhotoCapture.this,SuccessActivity.class);
                        startActivity(photoAuthenticate);
                    }else{
                        taskValidationProgress.dismiss();
                        validated=false;
                        Toast.makeText(getBaseContext(),"Authentication Validation Failed",Toast.LENGTH_SHORT).show();
                        Intent contactSupportIntent=new Intent(PhotoCapture.this,ContactSupportActvity.class);
                        startActivity(contactSupportIntent);
                    }

                }else{
                    taskValidationProgress.dismiss();
                    validated=false;
                    Toast.makeText(getBaseContext(),"Authentication Validation Failed",Toast.LENGTH_SHORT).show();
                    Intent contactSupportIntent=new Intent(PhotoCapture.this,ContactSupportActvity.class);
                    startActivity(contactSupportIntent);
                }
            }

            @Override
            public void onFailure(Call<RecognizeKairosResponse> call, Throwable t) {

                taskValidationProgress.dismiss();
                validated=false;
                Toast.makeText(getBaseContext(),"Liveliness Validation Failed",Toast.LENGTH_SHORT).show();
                Intent contactSupportIntent=new Intent(PhotoCapture.this,ContactSupportActvity.class);
                startActivity(contactSupportIntent);

            }
        });
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        photoCapture.start();
    }

    @Override
    protected void onPause() {
        photoCapture.stop();
        super.onPause();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int mCameraWidth = right - left;
        int mCameraHeight = bottom - top;


        photoCapture.removeOnLayoutChangeListener(this);
    }
}
