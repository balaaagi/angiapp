package me.balaaagi.angi.network.models;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class DetectRequestModel {
    private String image;

    public DetectRequestModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
