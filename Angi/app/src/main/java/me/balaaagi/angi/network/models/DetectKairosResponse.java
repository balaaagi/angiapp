package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */


public class DetectKairosResponse {

    @SerializedName("Errors")
    List<KairosError> errors;

    @SerializedName("images")
    List<KairosDetectImages> images;




}
