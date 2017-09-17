package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class RecognizeKairosResponse {
    @SerializedName("Errors")
    List<KairosError> errors=new ArrayList<KairosError>();

    @SerializedName("images")
    List<KairosRecognizeImages> images=new ArrayList<KairosRecognizeImages>();

    public RecognizeKairosResponse(List<KairosError> errors, List<KairosRecognizeImages> images) {
        this.errors = errors;
        this.images = images;
    }

    public List<KairosError> getErrors() {
        return errors;
    }

    public void setErrors(List<KairosError> errors) {
        this.errors = errors;
    }

    public List<KairosRecognizeImages> getImages() {
        return images;
    }

    public void setImages(List<KairosRecognizeImages> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return images.toString();
    }

}

