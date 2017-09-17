package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */


public class DetectKairosResponse {

    @SerializedName("Errors")
    List<KairosError> errors=new ArrayList<KairosError>();

    @SerializedName("images")
    List<KairosDetectImages> images=new ArrayList<KairosDetectImages>();

    public DetectKairosResponse(List<KairosError> errors, List<KairosDetectImages> images) {
        this.errors = errors;
        this.images = images;
    }

    public List<KairosError> getErrors() {
        return errors;
    }

    public void setErrors(List<KairosError> errors) {
        this.errors = errors;
    }

    public List<KairosDetectImages> getImages() {
        return images;
    }

    public void setImages(List<KairosDetectImages> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return images.toString();
    }
}
