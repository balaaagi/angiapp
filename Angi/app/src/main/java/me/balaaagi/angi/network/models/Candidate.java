package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class Candidate {
    @SerializedName("subject_id")
    private String subject_id;

    @SerializedName("confidence")
    private double confidence;

    @SerializedName("enrollment_timestamp")
    private long enrollment_timestamp;

    public Candidate(String subject_id, double confidence, long enrollment_timestamp) {
        this.subject_id = subject_id;
        this.confidence = confidence;
        this.enrollment_timestamp = enrollment_timestamp;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public long getEnrollment_timestamp() {
        return enrollment_timestamp;
    }

    public void setEnrollment_timestamp(long enrollment_timestamp) {
        this.enrollment_timestamp = enrollment_timestamp;
    }
}
