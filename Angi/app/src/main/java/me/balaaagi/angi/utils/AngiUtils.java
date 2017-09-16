package me.balaaagi.angi.utils;

import android.graphics.Bitmap;
import android.support.compat.BuildConfig;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        kairosHeaderMap.put("Content-Type","application/json");
        kairosHeaderMap.put("app_id", me.balaaagi.angi.BuildConfig.KAIROS_APP_ID);
        kairosHeaderMap.put("app_key", me.balaaagi.angi.BuildConfig.KAIROS_API_KEY);
        return kairosHeaderMap;
    }
}
