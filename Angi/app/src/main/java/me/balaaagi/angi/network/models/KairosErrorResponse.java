package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class KairosErrorResponse {
    @SerializedName("Errors")
    List<KairosError> errors=new ArrayList<>();

    public KairosErrorResponse(List<KairosError> errors) {
        this.errors = errors;
    }

    public List<KairosError> getErrors() {
        return errors;
    }

    public void setErrors(List<KairosError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
