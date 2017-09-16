package me.balaaagi.angi.network;

import me.balaaagi.angi.network.models.DetectKairosResponse;
import me.balaaagi.angi.network.models.DetectRequestModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public interface KairosApiInterface {

    @POST("detect")
    Call<DetectKairosResponse> detectFacialFeatures(@Body DetectRequestModel requestModel);


}
