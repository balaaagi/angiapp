package me.balaaagi.angi.network.models;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class RecognizeRequestModel {

    private String image;
    private String gallery_name;

    public RecognizeRequestModel(String image, String gallery_name) {
        this.image = image;
        this.gallery_name = gallery_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGallery_name() {
        return gallery_name;
    }

    public void setGallery_name(String gallery_name) {
        this.gallery_name = gallery_name;
    }
}
