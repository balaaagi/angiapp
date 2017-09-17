package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class Transaction {

    @SerializedName("status")
    private String status;

    @SerializedName("subject_id")
    private String subject_id;

    @SerializedName("gallery_name")
    private String gallery_name;

    public Transaction(String status, String subject_id, String gallery_name) {
        this.status = status;
        this.subject_id = subject_id;
        this.gallery_name = gallery_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getGallery_name() {
        return gallery_name;
    }

    public void setGallery_name(String gallery_name) {
        this.gallery_name = gallery_name;
    }
}
