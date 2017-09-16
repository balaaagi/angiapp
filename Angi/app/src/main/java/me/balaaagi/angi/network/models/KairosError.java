package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class KairosError {
    @SerializedName("Message")
    private String message;

    @SerializedName("ErrCode")
    private String errCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
