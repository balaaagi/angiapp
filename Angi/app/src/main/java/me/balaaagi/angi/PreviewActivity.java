package me.balaaagi.angi;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
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
    ProgressDialog taskValidationProgress;
    int taskNo;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        taskNo = getIntent().getIntExtra("order",1);
        previewImage= (ImageView) findViewById(R.id.capturedImagePreview);
        bitmapOfFirstTask = ResultHolder.getImage();
        taskValidationProgress = new ProgressDialog(this);
        if (bitmapOfFirstTask == null) {
            finish();
            return;
        }

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://kolam-dd9a5.appspot.com");
//        uploadFile();
        previewImage.setImageBitmap(bitmapOfFirstTask);
        boolean output=validateFirstTask();
        
    }

    public void uploadFile(){
        Uri file = Uri.fromFile(new File(String.valueOf(AngiUtils.getImageUri(this,bitmapOfFirstTask))));
        if(AngiUtils.getImageUri(this,bitmapOfFirstTask)!=null){
            StorageReference riversRef = mStorageRef.child("colorise/"+AngiUtils.getImageUri(this,bitmapOfFirstTask).getLastPathSegment());

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.d("Preview ",downloadUrl.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Log.d("Preview ","Upload Failed");
                        }
                    });

        }else{
            Log.d("Preview ","Upload Failed");
        }
    }
    private boolean validateFirstTask() {
        Log.d("Preview","Coming inside");
        KairosApiInterface kairosClient=APIClient.getRetrofitClient().create(KairosApiInterface.class);
        Call<DetectKairosResponse> detectKairosResponseCall=kairosClient.detectFacialFeatures("application/json",BuildConfig.KAIROS_APP_ID,BuildConfig.KAIROS_API_KEY,new DetectRequestModel(AngiUtils.bitMapToBase64(bitmapOfFirstTask)));
        Log.d("Preview",detectKairosResponseCall.request().url().toString());
        Log.d("Preview",detectKairosResponseCall.request().headers().toString());

        taskValidationProgress.setIndeterminate(false);
        taskValidationProgress.setMessage("Validating Your Liveliness ...");
        taskValidationProgress.setTitle("Angi App");
        taskValidationProgress.show();
        detectKairosResponseCall.enqueue(new Callback<DetectKairosResponse>() {
            @Override
            public void onResponse(Call<DetectKairosResponse> call, Response<DetectKairosResponse> response) {
                Log.d("Preview","OKOK");
                List<KairosError> errors = response.body().getErrors();
                List<KairosDetectImages> imagesResponse = response.body().getImages();
                if(imagesResponse!=null){

                    Log.d("Preview",response.body().toString());

                    KairosDetectImages responseImage = imagesResponse.get(0);
                    if(imagesResponse.get(0).getStatus().equals("Complete")){
                        Log.d("Preview-TaskNumber",String.valueOf(taskNo));
                        Log.d("Preview- Roll",String.valueOf(responseImage.getFaces().get(0).getRoll()));
                        Log.d("Preview- Yaw",String.valueOf(responseImage.getFaces().get(0).getYaw()));
                        Log.d("Preview -Lips",String.valueOf(responseImage.getFaces().get(0).getAttributes().getLips()));
                        Log.d("Preview -Pitch",String.valueOf(responseImage.getFaces().get(0).getPitch()));
                        if(taskNo==1){
                            if(responseImage.getFaces().get(0).getAttributes().getLips().equals("Apart")){
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_icon));
                            }else{
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon));
                            }
                        } else if (taskNo == 2) {
                            if((responseImage.getFaces().get(0).getYaw()>10)||(responseImage.getFaces().get(0).getYaw()<-10)){
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_icon));
                            }else{
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon));
                            }
                        }else{
                            if((responseImage.getFaces().get(0).getYaw()>10)||(responseImage.getFaces().get(0).getYaw()<-10)){
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_icon));
                            }else{
                                taskValidationProgress.dismiss();
                                previewImage.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon));
                            }

                        }
//                        previewImage.setImageDrawable(getResources().getDrawable(R.drawable.tick_icon));
                        Toast.makeText(getBaseContext(),"First Task Completed",Toast.LENGTH_SHORT).show();
                    }else{
                        taskValidationProgress.dismiss();
                        previewImage.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon));
                        Toast.makeText(getBaseContext(),"Seems Like You have Not Performed the Movement",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    taskValidationProgress.dismiss();
                    previewImage.setImageDrawable(getResources().getDrawable(R.drawable.cross_icon));
                    Toast.makeText(getBaseContext(),"Seems Like You have Not Performed the Movement",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetectKairosResponse> call, Throwable t) {
                Log.d("Preview",t.getMessage());
                taskValidationProgress.dismiss();
                Toast.makeText(getApplicationContext(),"Some Issue",Toast.LENGTH_SHORT).show();
            }
        });

        return false;
    }
}
