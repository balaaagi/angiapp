package me.balaaagi.angi.network;

import java.util.Map;

import me.balaaagi.angi.network.models.DetectKairosResponse;
import me.balaaagi.angi.network.models.DetectRequestModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public interface KairosApiInterface {

    @POST("detect")
    Call<DetectKairosResponse> detectFacialFeatures(@Header("Content-Type") String content_type,@Header("app_id") String app_id,@Header("app_key") String app_key, @Body DetectRequestModel requestModel);


}
