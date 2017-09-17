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

    public KairosRecognizeImages(Transaction transaction, List<Candidate> candidates) {
        this.transaction = transaction;
        this.candidates = candidates;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
