package com.example.sampleiotclient.pojo.index;
import com.example.sampleiotclient.pojo.privacy.PrivacyChoice;

public class PrivacyChoiceResponse {

    private long id;
    private String localDateTime;
    private PrivacyChoice privacyChoice;

    public PrivacyChoiceResponse(){

    }

    public long getId() {
        return id;
    }

    public PrivacyChoiceResponse setId(long id) {
        this.id = id;
        return this;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public PrivacyChoiceResponse setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public PrivacyChoice getPrivacyChoice() {
        return privacyChoice;
    }

    public PrivacyChoiceResponse setPrivacyChoice(PrivacyChoice privacyChoice) {
        this.privacyChoice = privacyChoice;
        return this;
    }
}
