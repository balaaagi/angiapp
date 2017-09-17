package me.balaaagi.angi.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class KairosRecognizeImages {
    @SerializedName("transaction")
    private Transaction transaction;

    @SerializedName("candidates")
    private List<Candidate> candidates=new ArrayList<Candidate>();
}
