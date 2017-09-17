package me.balaaagi.angi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.compat.BuildConfig;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class AngiUtils {
    public static final String KAIROS_BASE_URL="https://api.kairos.com/";

    public static String bitMapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Object[] getDualAuthenticationTypeOrder(){
        List<Integer> solution = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            solution.add(i);
        }
        Collections.shuffle(solution);
        return solution.toArray();
    }

    public static Map<String,String> prepareKairosHeaderMap(){
        Map<String,String> kairosHeaderMap=new HashMap<String, String>();
        kairosHeaderMap.put("app_id", me.balaaagi.angi.BuildConfig.KAIROS_APP_ID);
        kairosHeaderMap.put("app_key", me.balaaagi.angi.BuildConfig.KAIROS_API_KEY);
        return kairosHeaderMap;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());



        File tempDir=  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Angi");
//        tempDir.mkdir();

        if (!tempDir.exists()) {

            if (!tempDir.mkdir()) {
                Log.d("Utils", "Oops! Failed create "
                        + "Angi" + " directory");
                return null;
            }

        }
        File tempFile = null;
        try {
            tempFile = File.createTempFile(tempDir.getPath()+File.separator+"IMG_" + timeStamp , ".jpg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] bitmapData = bytes.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        Log.d("Utils",tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(tempFile==null)
            return null;
        else
            return Uri.fromFile(tempFile);
//        return Uri.parse(path);
    }
}
