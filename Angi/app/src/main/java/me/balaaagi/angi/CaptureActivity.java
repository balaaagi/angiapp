package me.balaaagi.angi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.balaaagi.angi.utils.AngiUtils;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CaptureActivity extends AppCompatActivity implements View.OnLayoutChangeListener {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final String IMAGE_DIRECTORY_NAME = "Angi";

    private Uri fileUri;
    CameraView camera;
    Button clickButton;
    TextView task1_tview;
    private Object[] randomOrder;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        camera= (CameraView) findViewById(R.id.camera);
        camera.addOnLayoutChangeListener(this);
        task1_tview= (TextView) findViewById(R.id.task1_tview);
        randomOrder=AngiUtils.getDualAuthenticationTypeOrder();
        clickButton= (Button) findViewById(R.id.clicktoAunthenticate);
        if((int)randomOrder[0]==1){
            task1_tview.setText("Open Ur Mouth And Capture");
        }else if((int)randomOrder[0]==2){
            task1_tview.setText("Tilt Your Head Left Side And Capture");
        }else{
            task1_tview.setText("Tilt Your Head Right Side And Capture");
        }
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageForAuthenticate();
            }
        });

    }

    private void captureImageForAuthenticate() {
        final long startTime = System.currentTimeMillis();
        camera.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                long callbackTime = System.currentTimeMillis();
                Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
                ResultHolder.dispose();
                ResultHolder.setImage(bitmap);
                ResultHolder.setNativeCaptureSize(camera.getPreviewSize());
                ResultHolder.setTimeToCallback(callbackTime - startTime);
                Intent secondTaskintent = new Intent(CaptureActivity.this, PreviewActivity.class);
                secondTaskintent.putExtra("order",randomOrder);
                startActivity(secondTaskintent);
            }
        });
        camera.captureImage();

//        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        fileUri=getCaptureImageFileUri(MEDIA_TYPE_IMAGE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
//        startActivityForResult(cameraIntent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private Uri getCaptureImageFileUri(int mediaTypeImage) {
//        return  Uri.fromFile(getOutputMediaFile(mediaTypeImage));
//    }

//    /**
//     * Receiving activity result method will be called after closing the camera
//     * */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // if the result is capturing KairosDetectImages
//        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // successfully captured the image
//                // display it in image view
//                previewCapturedImage();
//            } else if (resultCode == RESULT_CANCELED) {
//                // user cancelled KairosDetectImages capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                // failed to capture image
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }

//    private void previewCapturedImage() {
//        try {
//            // hide video preview
//
//            captureImageView.setVisibility(View.VISIBLE);
//
//            // bimatp factory
//            BitmapFactory.Options options = new BitmapFactory.Options();
//
//            // downsizing image as it throws OutOfMemory Exception for larger
//            // images
//            options.inSampleSize = 8;
//
//            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
//                    options);
//
//            captureImageView.setImageBitmap(bitmap);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private static File getOutputMediaFile(int type) {
//
//        // External sdcard location
//        File mediaStorageDir = new File(
//                Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                IMAGE_DIRECTORY_NAME);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
//                        + IMAGE_DIRECTORY_NAME + " directory");
//                return null;
//            }
//        }
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//                Locale.getDefault()).format(new Date());
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + "IMG_" + timeStamp + ".jpg");
//        } else if (type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + "VID_" + timeStamp + ".mp4");
//        } else {
//            return null;
//        }
//
//        return mediaFile;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause() {
        camera.stop();
        super.onPause();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int mCameraWidth = right - left;
        int mCameraHeight = bottom - top;


        camera.removeOnLayoutChangeListener(this);
    }
}
